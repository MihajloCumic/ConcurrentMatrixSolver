package result.impl;

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
}
