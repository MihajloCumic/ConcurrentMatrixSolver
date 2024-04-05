package result.impl;

import matrix.Matrix;
import result.Result;

public class ErrorResult implements Result {
    private final String errorMessage;

    public ErrorResult(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorResult{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }

    @Override
    public String resultAsString() {
        return errorMessage;
    }

    @Override
    public boolean resultContainsMatrix(Matrix matrix) {
        return false;
    }
}
