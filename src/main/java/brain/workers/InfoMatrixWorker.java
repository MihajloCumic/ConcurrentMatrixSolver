package brain.workers;

import matrix.Matrix;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class InfoMatrixWorker implements Callable<String> {
    private final ConcurrentMap<String, Matrix> matrices;

    public InfoMatrixWorker(ConcurrentMap<String, Matrix> matrices){
        this.matrices = matrices;
    }
    @Override
    public String call() throws Exception {
        return null;
    }
}
