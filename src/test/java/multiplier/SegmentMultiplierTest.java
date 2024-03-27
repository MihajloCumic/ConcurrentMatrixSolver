package multiplier;

import matrix.Matrix;
import matrix.impl.MatrixImpl;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Random;

public class SegmentMultiplierTest {

    private static int from = 0;
    private static int to = 2;

    private static Matrix firstMatrix = createMatrix(5, 3);
    private static Matrix secondMatrix = createMatrix(3, 5);
    private static Matrix resultMatrix = new MatrixImpl(Path.of("/path"));


    public static void main(String[] args) {
        firstMatrix.printMatrix(true);
        secondMatrix.printMatrix(true);
        multiplyMatrixSegments();
        resultMatrix.printMatrix(true);
    }

    private static void multiplyMatrixSegments(){
        for(int i = 0; i < resultMatrix.getRowNumber(); i++){
            for(int j = 0; j < resultMatrix.getColNumber(); j++){
                resultMatrix.putElement(i,j, calculateCellValue(i, j));
            }
        }
    }

    private static BigInteger calculateCellValue(int row, int col){
        BigInteger value = BigInteger.ZERO;
        for(int i = from; i < to; i++){
            value = value.add(firstMatrix.getElement(row, i).multiply(secondMatrix.getElement(i, col)));
        }
        for(int i = to; i < secondMatrix.getRowNumber(); i++){
            value = value.add(firstMatrix.getElement(row, i).multiply(secondMatrix.getElement(i, col)));
        }
        return value;
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
