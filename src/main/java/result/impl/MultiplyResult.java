package result.impl;

import matrix.Matrix;
import result.Result;

public class MultiplyResult implements Result {

    private final String firstMatrixName;

    private final String secondMatrixName;

    private final String resultMatrixName;

    public MultiplyResult(String firstMatrixName, String secondMatrixName, String resultMatrixName) {
        this.firstMatrixName = firstMatrixName;
        this.secondMatrixName = secondMatrixName;
        this.resultMatrixName = resultMatrixName;

    }

    @Override
    public String resultAsString() {
        return "Result: " + firstMatrixName + " * " + secondMatrixName +  " = " + resultMatrixName;
    }

    @Override
    public boolean resultContainsMatrix(Matrix matrix) {
        return matrix.getName().equals(firstMatrixName) ||
                matrix.getName().equals(secondMatrixName) ||
                matrix.getName().equals(resultMatrixName);
    }

    @Override
    public String toString() {
        return "Result:\n\t" + firstMatrixName + " * " + secondMatrixName +  " = " + resultMatrixName;
    }
}
