package extractor.pool;

import extractor.thread.ExtractorWorker;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import task.Task;
import task.impl.CreateMatrixTask;
import task.impl.UpdateMatrixTask;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ExtractorPool {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Future<Matrix> submitTask(Task task){
        if(task instanceof CreateMatrixTask createMatrixTask){
//            Future<Matrix> matrixFuture = createMatrixTask.initiate();
//            Matrix matrix = matrixFuture.get();
//                matrix.printMatrix(true);
            Path potentialMatrixFile = createMatrixTask.getPotentialMatrixFile();
            int linesNum;
            try(Stream<String> lineStream = Files.lines(potentialMatrixFile)) {
                linesNum = (int)lineStream.count();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Future<Matrix> matrixFuture = forkJoinPool.submit(new ExtractorWorker(1200000,potentialMatrixFile, new MatrixImpl(potentialMatrixFile), 1, linesNum));
            return matrixFuture;
        }
        if(task instanceof UpdateMatrixTask updateMatrixTask){
            Future<?> matrixFuture = updateMatrixTask.initiate();
        }
        return null;
    }
}
