package brain;

import matrix.Matrix;
import queue.TaskQueue;
import result.Result;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Future;

public abstract class MatrixBrain {
    public abstract void cacheMatrix(Matrix matrix);
    public abstract void updateMatrix(Matrix matrix);
    public abstract void saveMatrixInFile(String matrixName, Path file);
    public abstract void clearMatrix(String matrixName);
    public abstract Future<List<Matrix>> getMatrixInfo();
    public abstract Future<Result> multiplyMatricesBlocking(String firstMatrixName, String secondMatrixName, String resultMatrixName);
    public abstract void multiplyMatricesAsync(String firstMatrixName, String secondMatrixName, String resultMatrixName);
    public abstract void shutdown();

    public static MatrixBrain newMatrixBrain(TaskQueue taskQueue){
        return null;
    }

}
