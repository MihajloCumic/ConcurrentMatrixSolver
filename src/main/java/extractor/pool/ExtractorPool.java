package extractor.pool;

import brain.MatrixBrain;
import extractor.thread.ExtractorWorker;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import task.Task;
import task.impl.CreateMatrixTask;
import task.impl.UpdateMatrixTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ExtractorPool {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final MatrixBrain matrixBrain;

    public ExtractorPool(MatrixBrain matrixBrain){
        this.matrixBrain = matrixBrain;
    }

    public void submitTask(Task task){
        if(task instanceof CreateMatrixTask createMatrixTask){
            Path potentialMatrixFile = createMatrixTask.getPotentialMatrixFile();
            int linesNum;
            try(Stream<String> lineStream = Files.lines(potentialMatrixFile)) {
                linesNum = (int)lineStream.count();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Matrix matrix = forkJoinPool.submit(new ExtractorWorker(1200000,potentialMatrixFile, new MatrixImpl(potentialMatrixFile), 1, linesNum)).get();
                matrixBrain.cacheMatrix(matrix);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
