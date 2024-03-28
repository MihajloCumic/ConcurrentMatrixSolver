package task.impl;

import matrix.Matrix;
import task.Task;
import task.type.TaskType;

import java.util.concurrent.Future;

public class MultiplyMatrixTask implements Task<Matrix> {
    private final Matrix firstMatrix;
    private final Matrix secondMatrix;
    private final String resultMatrixName;

    public MultiplyMatrixTask(Matrix firstMatrix, Matrix secondMatrix, String resultMatrixName){
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        if(resultMatrixName.isBlank() || resultMatrixName.isEmpty()){
            this.resultMatrixName = firstMatrix.getName().concat(secondMatrix.getName());
        }else {
            this.resultMatrixName = resultMatrixName;
        }
    }
    @Override
    public TaskType getType() {
        return TaskType.MULTIPLY;
    }

    @Override
    public Future<Matrix> initiate() {
        return null;
    }
}
