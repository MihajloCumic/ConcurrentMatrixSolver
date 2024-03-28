package brain.workers;

import matrix.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class InfoMatrixWorker implements Callable<List<Matrix>> {
    private final ConcurrentMap<String, Matrix> matrices;

    public InfoMatrixWorker(ConcurrentMap<String, Matrix> matrices){
        this.matrices = matrices;
    }
    @Override
    public List<Matrix> call() throws Exception {
        List<Matrix> resultMatrices = new ArrayList<>();
        matrices.forEach((key, value) -> resultMatrices.add(value));
        return resultMatrices;
    }
}
