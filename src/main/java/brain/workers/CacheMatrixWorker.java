package brain.workers;

import matrix.Matrix;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CacheMatrixWorker implements Runnable{

    private final ConcurrentMap<String, Matrix> matrices;
    private final Matrix matrix;

    public CacheMatrixWorker(ConcurrentMap<String, Matrix> matrices, Matrix matrix){
        this.matrices = matrices;
        this.matrix = matrix;
    }
    @Override
    public void run() {
        if(matrices.containsKey(matrix.getName())) return;
        matrices.putIfAbsent(matrix.getName(), matrix);
    }
}
