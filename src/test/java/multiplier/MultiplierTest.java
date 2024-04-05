package multiplier;

import matrix.Matrix;
import matrix.impl.MatrixImpl;
import multiplier.pool.MultiplierPool;
import multiplier.thread.MultiplierWorker;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;


public class MultiplierTest {
    private static String[][] values1 = {
            {"1", "2", "3", "4", "5"},
            {"11", "12", "13", "14", "15"},
            {"21", "22", "23", "24", "25"},
            {"31", "32", "33", "34", "35"},
            {"41", "42", "34", "44", "45"}
    };

    private static String[][] small1 = {
            {"1", "2", "3"},
            {"1", "2", "3"},
    };
    private static String[][] small2 = {
            {"1", "2"},
            {"1", "2"},
            {"1", "2"}

    };
    public static void main(String[] args) {
        MatrixImpl matrix1 = new MatrixImpl("prva", 5, 5);
        for(int i = 0; i < small1.length; i++){
            for(int j = 0; j < small1[0].length; j++){
                matrix1.getMatrix()[i][j] = new BigInteger(small1[i][j]);
            }
        }
        MatrixImpl matrix2 = new MatrixImpl("druga", 10, 5);
        for(int i = 0; i < small2.length; i++){
            for(int j = 0; j < small2[0].length; j++){
                matrix2.getMatrix()[i][j] = new BigInteger(small2[i][j]);
            }
        }
//        matrix2.printMatrix(false);
        MatrixImpl matrix3 = new MatrixImpl("rez", 2, 2);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            Matrix result = forkJoinPool.submit(new MultiplierWorker(1, matrix1, matrix2, matrix3,0 , 3)).get();
            result.printMatrix(false);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
