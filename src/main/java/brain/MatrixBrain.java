package brain;

import brain.workers.*;
import matrix.Matrix;
import queue.TaskQueue;
import result.Result;
import task.impl.PoisonPill;

import java.nio.file.Path;
import java.util.List;
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

    public void updateMatrix(Matrix matrix){
        System.out.println("Updating matrix:" + matrix.getName());
        executorService.submit(new UpdateMatrixWorker(matrices, matrix));
    }

    public void saveMatrixInFile(String matrixName, Path file){
        try {
            String message = executorService.submit(new SaveFileWorker(matrixName, file, matrices)).get();
            System.out.println(message);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("My message:");
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void clearMatrix(String matrixName){
        executorService.submit(new ClearMatrixWorker(matrixName, matrices, resultCache));

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

    public void shutdown(){
        taskQueue.addTask(new PoisonPill());
        executorService.shutdown();
        System.out.println("Matrix Brain shutdown");
    }




}
