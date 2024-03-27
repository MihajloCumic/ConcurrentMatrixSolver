package multiplier;

import extractor.thread.ExtractorWorker;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import multiplier.thread.MultiplierWorker;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class MultiplierWorkerTest {
    private static Matrix firstMatrix = createMatrix(3, 3);
    private static Matrix secondMatrix = createMatrix(3, 3);
    private static Matrix resultMatrix = new MatrixImpl( Path.of("/path"));
    public static void main(String[] args) {
        firstMatrix.printMatrix(true);
        secondMatrix.printMatrix(true);
        ForkJoinPool pool = new ForkJoinPool();

        try {
            Matrix matrix1 = pool.submit(new MultiplierWorker(3, firstMatrix, secondMatrix, resultMatrix, 0, 3)).get();
            matrix1.printMatrix(true);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static Matrix createMatrix(int rowNum, int colNum){
        Matrix matrix = new MatrixImpl(Path.of("/path"));
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                String random =  (int)((Math.random() * (10 - 1)) + 1) + "";
                matrix.putElement(i, j, new BigInteger(random));
            }
        }
        return matrix;
    }
}
