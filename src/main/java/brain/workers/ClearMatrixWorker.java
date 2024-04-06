package brain.workers;

import logger.GlobalLogger;
import matrix.Matrix;
import result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClearMatrixWorker implements Runnable{
    private final String matrixName;
    private final ConcurrentMap<String, Matrix> matrices;
    private final ConcurrentMap<String, Future<Result>> results;

    public ClearMatrixWorker(String matrixName, ConcurrentMap<String, Matrix> matrices, ConcurrentMap<String, Future<Result>> results) {
        this.matrixName = matrixName;
        this.matrices = matrices;
        this.results = results;
    }

    @Override
    public void run() {
        if(!matrices.containsKey(matrixName)) {
            GlobalLogger.getInstance().logError("Matrix with name: " + matrixName + " does not exist.");
            return;
        }
        Matrix matrix = matrices.get(matrixName);
        matrices.remove(matrixName);
        List<String> keysToRemove = new ArrayList<>();
        for(String key: results.keySet()){
            try {
                Result result = results.get(key).get();
                if(result.resultContainsMatrix(matrix)) keysToRemove.add(key);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        for(String key: keysToRemove){
            results.remove(key);
        }

        //obrisati putanju of matrice iz system explorera da bi je opet ucitao
    }
}
