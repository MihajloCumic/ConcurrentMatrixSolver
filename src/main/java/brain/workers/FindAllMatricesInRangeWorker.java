package brain.workers;

import matrix.Matrix;
import result.Result;
import result.impl.ErrorResult;
import result.impl.InfoResult;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class FindAllMatricesInRangeWorker implements Callable<Result> {
    private final ConcurrentMap<String, Matrix> matrices;

    private final boolean fromStart;
    private final int to;


    public FindAllMatricesInRangeWorker(ConcurrentMap<String, Matrix> matrices, boolean fromStart, int to) {
        this.matrices = matrices;
        this.fromStart = fromStart;
        this.to = to;
    }

    @Override
    public Result call() throws Exception {
        if(fromStart){
           return fromStartMatrices();
        }
        return fromEndMatrices();

    }

    private Result fromStartMatrices(){
        InfoResult infoResult = new InfoResult();
        if(matrices.size() <= to){
            for(String key: matrices.keySet()){
                infoResult.getMatrices().add(matrices.get(key));
            }
            return infoResult;
        }
        int cnt = 0;
        for(String key: matrices.keySet()){
            infoResult.getMatrices().add(matrices.get(key));
            cnt++;
            if(cnt >= to) break;
        }
        return infoResult;
    }

    private Result fromEndMatrices(){
        InfoResult infoResult = new InfoResult();
        if(to < 0){
            for(String key: matrices.keySet()){
                infoResult.getMatrices().add(matrices.get(key));
            }
            return infoResult;
        }
        int cnt = 0;
        int skipTo = matrices.size() - to;
        for(String key: matrices.keySet()){
            if(cnt < skipTo){
                cnt++;
                continue;
            }
            infoResult.getMatrices().add(matrices.get(key));
        }
        return infoResult;
    }
}
