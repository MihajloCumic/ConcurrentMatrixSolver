package extractor;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import extractor.pool.ExtractorPool;
import task.impl.CreateMatrixTask;
import task.impl.PoisonPill;
import task.impl.UpdateMatrixTask;

public abstract class Extractor {
    public abstract void submitTask(CreateMatrixTask createMatrixTask);
    public abstract void submitTask(UpdateMatrixTask updateMatrixTask);
    public abstract void submitTask(PoisonPill poisonPill);
    public static Extractor newMatrixExtractor(MatrixBrain matrixBrain, int limit){
        return new ExtractorPool(matrixBrain, limit);
    }


}
