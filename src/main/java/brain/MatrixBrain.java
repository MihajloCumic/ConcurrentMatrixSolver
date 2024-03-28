package brain;

import brain.workers.CacheMatrixWorker;
import brain.workers.InfoMatrixWorker;
import matrix.Matrix;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MatrixBrain {

    private final ExecutorService executorService;
    private final ConcurrentMap<String, Matrix> matrices;

    public MatrixBrain(ExecutorService executorService){
        this.executorService = executorService;
        this.matrices = new ConcurrentHashMap<>();
    }

    public void cacheMatrix(Matrix matrix){
        executorService.submit(new CacheMatrixWorker(matrices, matrix));
    }

    public void saveMatrixInFile(String matrixName, String path){

    }
    public Future<List<Matrix>> getMatrixInfo(){
        return executorService.submit(new InfoMatrixWorker(matrices));
    }

    public void multiplyMatricesBlocking(String firstMatrixName, String secondMatrixName){

    }

    public void multiplyMatricesAsync(String firstMatrixName, String secondMatrixName){

    }





}
