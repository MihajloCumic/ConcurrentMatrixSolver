package brain.workers;

import matrix.Matrix;
import matrix.impl.MatrixImpl;
import queue.TaskQueue;
import result.Result;
import result.impl.ErrorResult;
import result.impl.MultiplyResult;
import task.Task;
import task.impl.MultiplyMatrixTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

public class MultiplyMatrixWorker implements Callable<Result> {
    private final String firstMatrixName;
    private final String secondMatrixName;
    private String resultMatrixName;
    private final ConcurrentMap<String, Matrix> matrices;
    private final ConcurrentMap<String, Future<Result>> resultCache;
    private final TaskQueue taskQueue;

    public MultiplyMatrixWorker(TaskQueue taskQueue, ConcurrentMap<String, Matrix> matrices, ConcurrentMap<String, Future<Result>> resultCache, String firstMatrixName, String secondMatrixName, String resultMatrixName){
        this.taskQueue = taskQueue;
        this.matrices = matrices;
        this.resultCache = resultCache;
        this.firstMatrixName = firstMatrixName;
        this.secondMatrixName = secondMatrixName;
        this.resultMatrixName = resultMatrixName;
    }

    @Override
    public Result call() throws Exception {

        if(!matrices.containsKey(firstMatrixName))  return new ErrorResult("No matrix with name: " + firstMatrixName);
        if(!matrices.containsKey(secondMatrixName)) return new ErrorResult("No matrix with name: " + secondMatrixName);
        if(matrices.containsKey(resultMatrixName)) return new ErrorResult("Matrix with name: " + resultMatrixName + " already exists.");

        Matrix firstMatrix = matrices.get(firstMatrixName);
        Matrix secondMatrix = matrices.get(secondMatrixName);

        if(firstMatrix.getColNumber() != secondMatrix.getRowNumber()) return new ErrorResult("Matrices: " + firstMatrixName + ", " + secondMatrixName + " can not be multiplied.");

        Matrix resultMatrix = new MatrixImpl(resultMatrixName, firstMatrix.getRowNumber(), secondMatrix.getColNumber());

        MultiplyMatrixTask multiplyTask = new MultiplyMatrixTask(firstMatrix, secondMatrix, resultMatrix);
        taskQueue.addTask(multiplyTask);
        synchronized (multiplyTask){
            multiplyTask.wait();
        }
        return new MultiplyResult(firstMatrixName,  secondMatrixName, resultMatrixName);
    }
}
