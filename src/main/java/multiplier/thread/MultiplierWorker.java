package multiplier.thread;

import extractor.thread.ExtractorWorker;
import matrix.Matrix;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

public class MultiplierWorker extends RecursiveTask<Matrix> {
    private final int LIMIT;
    private final Matrix firstMatrix;
    private final Matrix secondMatrix;

    private final Matrix resultMatrix;

    private int from;
    private int to;

    public MultiplierWorker(int LIMIT, Matrix firstMatrix, Matrix secondMatrix, Matrix resultMatrix, int from, int to){
        this.LIMIT = LIMIT;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.from = from;
        this.to = to;

    }
    @Override
    protected Matrix compute() {
        if(to - from <= LIMIT){
            multiplyMatrixSegments();
        }else{
            //int mid = to % 2 == 0? to / 2 : (to / 2) + 1;
            int mid = from + LIMIT;
            MultiplierWorker left = new MultiplierWorker(LIMIT, firstMatrix, secondMatrix, resultMatrix, from, mid);
            MultiplierWorker right = new MultiplierWorker(LIMIT, firstMatrix, secondMatrix, resultMatrix, mid, to);

            left.fork();
            right.fork();
            left.join();
            right.join();
        }
        return resultMatrix;
    }

    private  void multiplyMatrixSegments(){
        for(int i = 0; i < resultMatrix.getRowNumber(); i++){
            for(int j = 0; j < resultMatrix.getColNumber(); j++){
                resultMatrix.putElement(i,j, calculateCellPartialValue(i, j));
            }
        }
    }

    private  BigInteger calculateCellPartialValue(int row, int col){
        BigInteger value = BigInteger.ZERO;
        for(int i = from; i < to; i++){
            value = value.add(firstMatrix.getElement(row, i).multiply(secondMatrix.getElement(i, col)));
        }
        return value;
    }


}
