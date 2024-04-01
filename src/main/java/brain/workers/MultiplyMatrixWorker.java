package brain.workers;

import matrix.Matrix;
import matrix.impl.MatrixImpl;
import queue.TaskQueue;
import result.Result;
import result.impl.MultiplyResult;
import task.Task;
import task.impl.MultiplyMatrixTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class MultiplyMatrixWorker implements Callable<Result> {
    private final String firstMatrixName;
    private final String secondMatrixName;
    private String resultMatrixName;
    private final ConcurrentMap<String, Matrix> matrices;
    private final TaskQueue taskQueue;

    public MultiplyMatrixWorker(TaskQueue taskQueue,ConcurrentMap<String, Matrix> matrices, String firstMatrixName, String secondMatrixName, String resultMatrixName){
        this.taskQueue = taskQueue;
        this.matrices = matrices;
        this.firstMatrixName = firstMatrixName;
        this.secondMatrixName = secondMatrixName;
        this.resultMatrixName = resultMatrixName;
    }

    @Override
    public Result call() throws Exception {
        if(!matrices.containsKey(firstMatrixName))  throw new RuntimeException("No matrix with name: " + firstMatrixName);
        if(!matrices.containsKey(secondMatrixName)) throw new RuntimeException("No matrix with name: " + secondMatrixName);
        if(resultMatrixName == null || resultMatrixName.isEmpty() || resultMatrixName.isBlank()) resultMatrixName = firstMatrixName.concat(secondMatrixName);

        Matrix firstMatrix = matrices.get(firstMatrixName);
        Matrix secondMatrix = matrices.get(secondMatrixName);

        if(firstMatrix.getColNumber() != secondMatrix.getRowNumber()) throw new RuntimeException("Matrices: " + firstMatrixName + ", " + secondMatrixName + " can not be multiplied.");

        Matrix resultMatrix = new MatrixImpl(resultMatrixName, firstMatrix.getRowNumber(), secondMatrix.getColNumber());

        MultiplyMatrixTask multiplyTask = new MultiplyMatrixTask(firstMatrix, secondMatrix, resultMatrix);
        taskQueue.addTask(multiplyTask);
        synchronized (multiplyTask){
            System.out.println("Waiting");
            multiplyTask.wait();
            System.out.println("Sleep");
            Thread.sleep(5000);
        }
        return new MultiplyResult(firstMatrixName, matrices.get(firstMatrixName).toString(), secondMatrixName, matrices.get(secondMatrixName).toString());
    }
}
