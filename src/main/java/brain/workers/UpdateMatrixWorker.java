package brain.workers;

import matrix.Matrix;

import java.util.concurrent.ConcurrentMap;

public class UpdateMatrixWorker implements Runnable{
    private final ConcurrentMap<String, Matrix> matrices;
    private final Matrix matrix;

    public UpdateMatrixWorker(ConcurrentMap<String, Matrix> matrices, Matrix matrix) {
        this.matrices = matrices;
        this.matrix = matrix;
    }

    @Override
    public void run() {
        if(!matrices.containsKey(matrix.getName())) return;
        matrices.put(matrix.getName(), matrix);
    }
}
