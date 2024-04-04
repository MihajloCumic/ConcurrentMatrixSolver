package multiplier;

import brain.MatrixBrain;
import multiplier.pool.MultiplierPool;
import task.impl.MultiplyMatrixTask;
import task.impl.PoisonPill;

public abstract class Multiplier {

    public abstract void submitTask(MultiplyMatrixTask multiplyTask);
    public abstract void submitTask(PoisonPill poisonPill);

    public static Multiplier newMatrixMultiplier(MatrixBrain matrixBrain){
        return new MultiplierPool(matrixBrain);
    }
}
