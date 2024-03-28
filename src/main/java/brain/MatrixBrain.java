package brain;

import matrix.Matrix;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class MatrixBrain {

    private final ExecutorService executorService;
    private final Map<String, Matrix> matrices;

    public MatrixBrain(ExecutorService executorService){
        this.executorService = executorService;
        this.matrices = new ConcurrentHashMap<>();
    }

    public void saveMatrix(Matrix matrix){

    }

    public void saveMatrixInFile(String matrixName, String path){

    }
    public String getMatrixInfo(String params){
        return null;
    }

    public void multiplyMatricesBlocking(String firstMatrixName, String secondMatrixName){

    }

    public void multiplyMatricesAsync(String firstMatrixName, String secondMatrixName){

    }





}
