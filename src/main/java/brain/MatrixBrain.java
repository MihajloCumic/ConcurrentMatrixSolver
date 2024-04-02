package brain;

import brain.workers.CacheMatrixWorker;
import brain.workers.InfoMatrixWorker;
import brain.workers.MultiplyMatrixWorker;
import matrix.Matrix;
import queue.TaskQueue;
import result.Result;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MatrixBrain {

    private final ExecutorService executorService;
    private final ConcurrentMap<String, Matrix> matrices;
    private final ConcurrentMap<String, Future<Result>> resultCache;

    private final TaskQueue taskQueue;

    public MatrixBrain(ExecutorService executorService, TaskQueue taskQueue){
        this.executorService = executorService;
        this.matrices = new ConcurrentHashMap<>();
        this.taskQueue = taskQueue;
        this.resultCache = new ConcurrentHashMap<>();
    }

    public void cacheMatrix(Matrix matrix){
        executorService.submit(new CacheMatrixWorker(matrices, matrix));
    }

    public void saveMatrixInFile(String matrixName, String path){

    }
    public Future<List<Matrix>> getMatrixInfo(){
        return executorService.submit(new InfoMatrixWorker(matrices));
    }

    public Future<Result> multiplyMatricesBlocking(String firstMatrixName, String secondMatrixName, String resultMatrixName){
        String key = "mul"+firstMatrixName+secondMatrixName+resultMatrixName;
        if(resultCache.containsKey(key)){
            try {
                Result result = resultCache.get(key).get();
                System.out.println(resultCache.get(key).isDone());
                System.out.println(result.toString());
                return resultCache.get(key);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        Future<Result> resultFuture = executorService.submit(new MultiplyMatrixWorker(taskQueue, matrices, resultCache,firstMatrixName, secondMatrixName, resultMatrixName));
        resultCache.put(key, resultFuture);
        return resultFuture;
    }

    public void multiplyMatricesAsync(String firstMatrixName, String secondMatrixName, String resultMatrixName){
        String key = "mul"+firstMatrixName+secondMatrixName+resultMatrixName;
        if(resultCache.containsKey(key)){
            try {
                Future<Result> resultFuture = resultCache.get(key);
                if(resultFuture.isDone()){
                    System.out.println("The multiplication is done");
                    System.out.println(resultFuture.get().toString());
                    return;
                }
                System.out.println("Still multiplying.");
                return;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        Future<Result> resultFuture = executorService.submit(new MultiplyMatrixWorker(taskQueue, matrices, resultCache,firstMatrixName, secondMatrixName, resultMatrixName));
        resultCache.put(key, resultFuture);
    }





}
