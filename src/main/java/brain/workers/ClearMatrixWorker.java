package brain.workers;

import matrix.Matrix;
import result.Result;

import java.util.concurrent.ConcurrentMap;
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
        if(!matrices.containsKey(matrixName)) return;
        matrices.remove(matrixName);
        //obrisati putanju of matrice iz system explorera da bi je opet ucitao
    }
}
