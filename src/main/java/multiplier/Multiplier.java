package multiplier;

import brain.pool.MatrixBrainPool;
import multiplier.pool.MultiplierPool;
import task.impl.MultiplyMatrixTask;
import task.impl.PoisonPill;

public abstract class Multiplier {

    public abstract void submitTask(MultiplyMatrixTask multiplyTask);
    public abstract void submitTask(PoisonPill poisonPill);

    public static Multiplier newMatrixMultiplier(MatrixBrainPool matrixBrainPool){
        return new MultiplierPool(matrixBrainPool);
    }
}
