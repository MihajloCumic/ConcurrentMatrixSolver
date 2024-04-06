package brain.workers;

import logger.GlobalLogger;
import matrix.Matrix;
import result.Result;
import system.explorer.SystemExplorer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClearMatrixWorker implements Runnable{
    private final String matrix;
    private final boolean isMatrixName;

    private final SystemExplorer systemExplorer;
    private final ConcurrentMap<String, Matrix> matrices;
    private final ConcurrentMap<String, Future<Result>> results;



    public ClearMatrixWorker(String matrix,boolean isMatrixName, SystemExplorer systemExplorer,ConcurrentMap<String, Matrix> matrices, ConcurrentMap<String, Future<Result>> results) {
        this.matrix = matrix;
        this.isMatrixName = isMatrixName;
        this.systemExplorer = systemExplorer;
        this.matrices = matrices;
        this.results = results;
    }

    @Override
    public void run() {
        String matrixName = null;
        String fileName = null;
        if(isMatrixName){
            matrixName = matrix;
            fileName = matrices.get(matrixName).getFilePath();
        }else{
            for(String key: matrices.keySet()){
                Matrix tmpMatrix = matrices.get(key);
                if(tmpMatrix.getFilePath().equals(matrix)){
                    matrixName = tmpMatrix.getName();
                    fileName = tmpMatrix.getFilePath();
                    break;
                }
            }
        }
        if(matrixName == null || fileName == null) {
            GlobalLogger.getInstance().logError("Matrix: " + matrixName + "or file: "+ fileName+ " does not exist.");
            return;
        }
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

        GlobalLogger.getInstance().logInfo("MB removed matrix + " + matrixName);
        systemExplorer.removePath(fileName);
    }
}
