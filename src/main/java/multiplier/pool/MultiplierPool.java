package multiplier.pool;

import brain.MatrixBrain;
import matrix.Matrix;
import multiplier.thread.MultiplierWorker;
import task.Task;
import task.impl.MultiplyMatrixTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class MultiplierPool {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final MatrixBrain matrixBrain;

    public MultiplierPool(MatrixBrain matrixBrain){
        this.matrixBrain = matrixBrain;
    }

    public void submitTask(Task task){
        if(task instanceof MultiplyMatrixTask multiplyTask){
            try {
                Matrix matrix = forkJoinPool.submit(new MultiplierWorker(94,
                        multiplyTask.getFirstMatrix(),
                        multiplyTask.getSecondMatrix(),
                        multiplyTask.getResultMatrix(),
                        0,
                        multiplyTask.getFirstMatrix().getColNumber())).get();
                matrixBrain.cacheMatrix(matrix);

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
