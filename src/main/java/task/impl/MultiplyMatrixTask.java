package task.impl;

import matrix.Matrix;
import task.Task;
import task.type.TaskType;

import java.util.concurrent.Future;

public class MultiplyMatrixTask implements Task<Matrix> {
    private final Matrix firstMatrix;
    private final Matrix secondMatrix;
    private final Matrix resultMatrix;

    public MultiplyMatrixTask(Matrix firstMatrix, Matrix secondMatrix, Matrix resultMatrix){
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
    }
    @Override
    public TaskType getType() {
        return TaskType.MULTIPLY;
    }

    @Override
    public Future<Matrix> initiate() {
        return null;
    }

    public Matrix getFirstMatrix() {
        return firstMatrix;
    }

    public Matrix getSecondMatrix() {
        return secondMatrix;
    }

    public Matrix getResultMatrix() {
        return resultMatrix;
    }
}
