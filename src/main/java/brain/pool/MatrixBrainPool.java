package brain.pool;

import brain.MatrixBrain;
import brain.workers.*;
import matrix.Matrix;
import queue.TaskQueue;
import result.Result;
import task.impl.PoisonPill;

import java.nio.file.Path;
import java.util.concurrent.*;

public class MatrixBrainPool extends MatrixBrain {

    private final ExecutorService executorService;
    private final ConcurrentMap<String, Matrix> matrices;
    private final ConcurrentMap<String, Future<Result>> resultCache;

    private final TaskQueue taskQueue;

    public MatrixBrainPool(ExecutorService executorService, TaskQueue taskQueue){
        this.executorService = executorService;
        this.matrices = new ConcurrentHashMap<>();
        this.taskQueue = taskQueue;
        this.resultCache = new ConcurrentHashMap<>();
    }

    @Override
    public void cacheMatrix(Matrix matrix){
        executorService.submit(new CacheMatrixWorker(matrices, matrix));
    }

    @Override
    public void updateMatrix(Matrix matrix){
        System.out.println("Updating matrix:" + matrix.getName());
        executorService.submit(new UpdateMatrixWorker(matrices, matrix));
    }

    @Override
    public void saveMatrixInFile(String matrixName, Path file){
        executorService.submit(new SaveFileWorker(matrixName, file, matrices));
    }

    @Override
    public void clearMatrix(String matrixName){
        executorService.submit(new ClearMatrixWorker(matrixName, matrices, resultCache));

    }
    @Override
    public void getMatrixInfo(String matrixName){
        try {
            Result result =  executorService.submit(new FindMatrixInfoWorker(matrixName, matrices)).get();
            System.out.println(result.resultAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAllMatricesInfo() {
        try {
            Result result =  executorService.submit(new FindAllMatricesInfo(matrices)).get();
            System.out.println(result.resultAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAllMatricesInRangeInfo(boolean fromStart, int to) {
        try {
            Result result =  executorService.submit(new FindAllMatricesInRangeWorker(matrices, fromStart, to)).get();
            System.out.println(result.resultAsString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void multiplyMatricesBlocking(String firstMatrixName, String secondMatrixName, String resultMatrixName){
        String key = "mul"+firstMatrixName+secondMatrixName+resultMatrixName;
        if(resultCache.containsKey(key)){
            try {
                Result result = resultCache.get(key).get();
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Future<Result> resultFuture = executorService.submit(new MultiplyMatrixWorker(taskQueue, matrices, resultCache,firstMatrixName, secondMatrixName, resultMatrixName));
            resultCache.put(key, resultFuture);
            System.out.println(resultFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

    @Override
    public void shutdown(){
        taskQueue.addTask(new PoisonPill());
        executorService.shutdown();
        System.out.println("Matrix Brain shutdown");
    }




}
