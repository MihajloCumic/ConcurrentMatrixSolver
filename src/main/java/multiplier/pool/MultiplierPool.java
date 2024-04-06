package multiplier.pool;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import logger.GlobalLogger;
import matrix.Matrix;
import multiplier.Multiplier;
import multiplier.thread.MultiplierWorker;
import task.impl.MultiplyMatrixTask;
import task.impl.PoisonPill;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;


public class MultiplierPool extends Multiplier {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();
    private final MatrixBrain matrixBrain;
    private final int maxRowsSize;
    private final boolean test;

    public MultiplierPool(MatrixBrain matrixBrain, int maxRowsSize, boolean test) {
        this.matrixBrain = matrixBrain;
        this.maxRowsSize = maxRowsSize;
        this.test = test;
    }

    @Override
    public void submitTask(MultiplyMatrixTask multiplyTask) {
        try {
            Matrix matrix = forkJoinPool.submit(new MultiplierWorker(maxRowsSize,
                    multiplyTask.getFirstMatrix(),
                    multiplyTask.getSecondMatrix(),
                    multiplyTask.getResultMatrix(),
                    0,
                    multiplyTask.getFirstMatrix().getColNumber())).get();
            matrixBrain.cacheMatrix(matrix);
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
        GlobalLogger.getInstance().logInfo("MultiplierPool has shutdown.");
    }
}
