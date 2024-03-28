package brain;

import brain.workers.CacheMatrixWorker;
import brain.workers.InfoMatrixWorker;
import brain.workers.MultiplyMatrixWorker;
import matrix.Matrix;
import queue.TaskQueue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MatrixBrain {

    private final ExecutorService executorService;
    private final ConcurrentMap<String, Matrix> matrices;
    private final TaskQueue taskQueue;

    public MatrixBrain(ExecutorService executorService, TaskQueue taskQueue){
        this.executorService = executorService;
        this.matrices = new ConcurrentHashMap<>();
        this.taskQueue = taskQueue;
    }

    public void cacheMatrix(Matrix matrix){
        executorService.submit(new CacheMatrixWorker(matrices, matrix));
    }

    public void saveMatrixInFile(String matrixName, String path){

    }
    public Future<List<Matrix>> getMatrixInfo(){
        return executorService.submit(new InfoMatrixWorker(matrices));
    }

    public Future<String> multiplyMatricesBlocking(String firstMatrixName, String secondMatrixName, String resultMatrixName){
            return executorService.submit(new MultiplyMatrixWorker(taskQueue, matrices, firstMatrixName, secondMatrixName, resultMatrixName));
    }

    public void multiplyMatricesAsync(String firstMatrixName, String secondMatrixName){

    }





}
