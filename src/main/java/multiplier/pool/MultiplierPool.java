package multiplier.pool;

import brain.pool.MatrixBrainPool;
import matrix.Matrix;
import multiplier.Multiplier;
import multiplier.thread.MultiplierWorker;
import task.impl.MultiplyMatrixTask;
import task.impl.PoisonPill;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;


public class MultiplierPool extends Multiplier {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final MatrixBrainPool matrixBrainPool;

    public MultiplierPool(MatrixBrainPool matrixBrainPool) {
        this.matrixBrainPool = matrixBrainPool;
    }

    @Override
    public void submitTask(MultiplyMatrixTask multiplyTask) {

        try {
            Matrix matrix = forkJoinPool.submit(new MultiplierWorker(10,
                    multiplyTask.getFirstMatrix(),
                    multiplyTask.getSecondMatrix(),
                    multiplyTask.getResultMatrix(),
                    0,
                    multiplyTask.getFirstMatrix().getColNumber())).get();
            matrixBrainPool.cacheMatrix(matrix);
            synchronized (multiplyTask) {
                multiplyTask.notify();
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void submitTask(PoisonPill poisonPill) {
        forkJoinPool.shutdown();
        System.out.println("Extractor shutdown.");
    }
}
