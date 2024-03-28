package brain.workers;

import matrix.Matrix;
import matrix.impl.MatrixImpl;
import queue.TaskQueue;
import task.Task;
import task.impl.MultiplyMatrixTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class MultiplyMatrixWorker implements Callable<String> {
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
    public String call() throws Exception {
        if(!matrices.containsKey(firstMatrixName))  return "No matrix with name: " + firstMatrixName;
        if(!matrices.containsKey(secondMatrixName)) return "No matrix with name: " + secondMatrixName;
        if(resultMatrixName == null || resultMatrixName.isEmpty() || resultMatrixName.isBlank()) resultMatrixName = firstMatrixName.concat(secondMatrixName);

        Matrix firstMatrix = matrices.get(firstMatrixName);
        Matrix secondMatrix = matrices.get(secondMatrixName);

        if(firstMatrix.getColNumber() != secondMatrix.getRowNumber()) return "Matrices: " + firstMatrixName + ", " + secondMatrixName + " can not be multiplied.";

        Matrix resultMatrix = new MatrixImpl(resultMatrixName, firstMatrix.getRowNumber(), secondMatrix.getColNumber());

        Task<Matrix> multiplyTask = new MultiplyMatrixTask(firstMatrix, secondMatrix, resultMatrix);
        taskQueue.addTask(multiplyTask);

        return "Started multiplying matrices: " + firstMatrixName + ", " + secondMatrixName;
    }
}
