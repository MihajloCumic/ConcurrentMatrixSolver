package brain.workers;

import matrix.Matrix;
import result.Result;
import result.impl.InfoResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class FindAllMatricesInfo implements Callable<Result> {
    private final ConcurrentMap<String, Matrix> matrices;

    public FindAllMatricesInfo(ConcurrentMap<String, Matrix> matrices) {
        this.matrices = matrices;
    }

    @Override
    public Result call() throws Exception {
        InfoResult result = new InfoResult();
        for(String key: matrices.keySet()){
            result.getMatrices().add(matrices.get(key));
        }
        return result;
    }
}
