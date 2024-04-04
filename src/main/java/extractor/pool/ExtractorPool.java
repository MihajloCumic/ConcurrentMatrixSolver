package extractor.pool;

import brain.MatrixBrain;
import extractor.thread.ExtractorWorker;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import task.Task;
import task.impl.CreateMatrixTask;
import task.impl.UpdateMatrixTask;
import task.type.TaskType;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ExtractorPool {
    private final int BYTES_LIMIT = 1024;
    private final ExecutorService executorService;
    private final ExecutorCompletionService<Matrix> completionService;
    private final MatrixBrain matrixBrain;

    public ExtractorPool(MatrixBrain matrixBrain){
        this.executorService = Executors.newCachedThreadPool();
        this.completionService = new ExecutorCompletionService<>(this.executorService);
        this.matrixBrain = matrixBrain;
    }

    public void submitTask(Task task){
        if(task.getType().equals(TaskType.POISON_PILL)){
            executorService.shutdown();
            System.out.println("Extractor pool shutdown.");
            return;
        }
        if(!task.getType().equals(TaskType.CREATE)) return;
        CreateMatrixTask createMatrixTask = (CreateMatrixTask) task;
        int numberOfJobs = divideTask(createMatrixTask);
        Matrix matrix = null;
        try {
            for(int i = 0; i < numberOfJobs; i++){
                matrix = completionService.take().get();
            }
            if(matrix != null){
                matrixBrain.cacheMatrix(matrix);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    private int divideTask(CreateMatrixTask task){
        int numberOfJobs = 0;
        Path file = task.getPotentialMatrixFile();
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

                if(tmp < BYTES_LIMIT){
                    byteCount = tmp;
                    endLine++;
                    continue;
                }
                if(tmp == BYTES_LIMIT){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfJobs;
    }
}
