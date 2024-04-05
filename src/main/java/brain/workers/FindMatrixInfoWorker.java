package brain.workers;

import matrix.Matrix;
import result.Result;
import result.impl.ErrorResult;
import result.impl.InfoResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class FindMatrixInfoWorker implements Callable<Result> {
    private final ConcurrentMap<String, Matrix> matrices;
    private final String matrixName;

    public FindMatrixInfoWorker(String matrixName,ConcurrentMap<String, Matrix> matrices){
        this.matrixName = matrixName;
        this.matrices = matrices;
    }
    @Override
    public Result call() throws Exception {
        if(!matrices.containsKey(matrixName)){
            return new ErrorResult("Matrix with name: " + matrixName + " does not exist.");
        }
        InfoResult result  = new InfoResult();
        result.getMatrices().add(matrices.get(matrixName));
        return result;
    }
}
