package brain;

import brain.pool.MatrixBrainPool;
import matrix.Matrix;
import queue.TaskQueue;
import result.Result;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class MatrixBrain {
    public abstract void cacheMatrix(Matrix matrix);
    public abstract void updateMatrix(Matrix matrix);
    public abstract void saveMatrixInFile(String matrixName, Path file);
    public abstract void clearMatrix(String matrixName);
    public abstract void getMatrixInfo(String matrixName);
    public abstract void getAllMatricesInfo();
    public abstract Future<Result> multiplyMatricesBlocking(String firstMatrixName, String secondMatrixName, String resultMatrixName);
    public abstract void multiplyMatricesAsync(String firstMatrixName, String secondMatrixName, String resultMatrixName);
    public abstract void shutdown();

    public static MatrixBrain newMatrixBrain(TaskQueue taskQueue){
        return new MatrixBrainPool(Executors.newCachedThreadPool(), taskQueue);
    }

}
