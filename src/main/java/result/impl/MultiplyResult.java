package result.impl;

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
        return null;
    }

    @Override
    public boolean resultContainsMatrix() {
        return false;
    }

    @Override
    public String toString() {
        return "Result:\n\t" + firstMatrixName + " * " + secondMatrixName +  " = " + resultMatrixName;
    }
}
