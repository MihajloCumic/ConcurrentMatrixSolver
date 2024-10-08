package extractor.pool;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import extractor.Extractor;
import extractor.thread.ExtractorWorker;
import logger.GlobalLogger;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import task.impl.CreateMatrixTask;
import task.impl.PoisonPill;
import task.impl.UpdateMatrixTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;

public class ExtractorPool extends Extractor {
    private final int bytesLimit;
    private final ExecutorService executorService;
    private final ExecutorCompletionService<Matrix> completionService;
    private final MatrixBrain matrixBrain;

    public ExtractorPool(MatrixBrain matrixBrain, int bytesLimit){
        this.executorService = Executors.newCachedThreadPool();
        this.completionService = new ExecutorCompletionService<>(this.executorService);
        this.matrixBrain = matrixBrain;
        this.bytesLimit = bytesLimit;
    }

    @Override
    public void submitTask(CreateMatrixTask task){
        Matrix matrix = extraxtMatrix(task.getPotentialMatrixFile());
        if(matrix == null) return;
        matrixBrain.cacheMatrix(matrix);
    }

    @Override
    public void submitTask(UpdateMatrixTask updateMatrixTask){
        Matrix matrix = extraxtMatrix(updateMatrixTask.getMatrixFile());
        if(matrix == null) return;
        matrixBrain.updateMatrix(matrix);
    }

    @Override
    public void submitTask(PoisonPill poisonPill){
        executorService.shutdown();
        synchronized (poisonPill){
            poisonPill.notify();
        }
        GlobalLogger.getInstance().logInfo("Extractor pool shutdown.");
    }
    private Matrix extraxtMatrix(Path file){
        int numberOfJobs = divideTask(file);
        Matrix matrix = null;
        try {
            for(int i = 0; i < numberOfJobs; i++){
                matrix = completionService.take().get();
            }
            return matrix;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private int divideTask(Path file){
        int numberOfJobs = 0;
        Matrix matrix = new MatrixImpl(file);

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            boolean isFirst = true;
            int byteCount = 0;
            int startLine = 1;
            int endLine = 1;
            String line;

            while ((line = reader.readLine()) != null) {
                if(isFirst){
                    isFirst = false;
                    continue;
                }
                int tmp = byteCount + line.getBytes(StandardCharsets.UTF_8).length;

                if(tmp < bytesLimit){
                    byteCount = tmp;
                    endLine++;
                    continue;
                }
                if(tmp == bytesLimit){
                    byteCount = 0;
                    completionService.submit(new ExtractorWorker(file, matrix, startLine, endLine));
                    numberOfJobs++;
                    startLine = endLine;
                    endLine++;
                    continue;

                }
                byteCount = line.getBytes(StandardCharsets.UTF_8).length;
                completionService.submit(new ExtractorWorker(file, matrix, startLine, endLine - 1));
                numberOfJobs++;
                startLine = endLine - 1;
                endLine++;

            }
            if(byteCount < 1024 && byteCount > 0){
                completionService.submit(new ExtractorWorker(file, matrix, startLine, endLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfJobs;
    }
}
