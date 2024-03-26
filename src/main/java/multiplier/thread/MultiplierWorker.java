package multiplier.thread;

import matrix.Matrix;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

public class MultiplierWorker extends RecursiveTask<Matrix> {
    private final int LIMIT;
    private final Matrix firstMatrix;
    private final Matrix secondMatrix;

    private final Matrix resultMatrix;

    private final int rowNum;

    private int from;
    private int to;

    public MultiplierWorker(int LIMIT, Matrix firstMatrix, Matrix secondMatrix, Matrix resultMatrix, int from, int to, int rowNum){
        this.LIMIT = LIMIT;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.from = from;
        this.to = to;
        this.rowNum = rowNum;
    }
    @Override
    protected Matrix compute() {
        return null;
    }

    private void multiplyMatrixSegments(){
        for (int row = 0; row < rowNum; row++) {
            for (int col = from; col < to; col++) {
                BigInteger prevValue = resultMatrix.getElement(row, col);
                resultMatrix.putElement(row, col, prevValue.add(multiplyMatrixCell(row, col)));
            }
        }
    }
    private BigInteger multiplyMatrixCell(int row, int col){
        BigInteger cell = BigInteger.ZERO;
        for (int i = from; i < to ; i++) {
             cell = cell.add(firstMatrix.getElement(row, i).multiply(secondMatrix.getElement(i, col)));
        }
        return cell;
    }
}
