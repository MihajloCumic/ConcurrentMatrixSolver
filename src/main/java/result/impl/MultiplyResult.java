package result.impl;

import result.Result;

public class MultiplyResult implements Result {

    private final String firstMatrixName;
    private final String firstMatrixFile;

    private final String secondMatrixName;
    private final String secondMatrixFile;

    public MultiplyResult(String firstMatrixName, String firstMatrixFile, String secondMatrixName, String secondMatrixFile) {
        this.firstMatrixName = firstMatrixName;
        this.firstMatrixFile = firstMatrixFile;
        this.secondMatrixName = secondMatrixName;
        this.secondMatrixFile = secondMatrixFile;
    }

    @Override
    public String toString() {
        return "MultiplyResult{" +
                "firstMatrixName='" + firstMatrixName + '\'' +
                ", firstMatrixFile='" + firstMatrixFile + '\'' +
                ", secondMatrixName='" + secondMatrixName + '\'' +
                ", secondMatrixFile='" + secondMatrixFile + '\'' +
                '}';
    }
}
