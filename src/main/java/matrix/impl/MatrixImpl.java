package matrix.impl;

import matrix.Matrix;

import java.math.BigInteger;
import java.nio.file.Path;

public class MatrixImpl implements Matrix {
    private final String name;
    private final Path file;

    private final int rowNum;
    private final int colNum;

    private final BigInteger[][] matrix;

    public MatrixImpl(String name, Path file, int rowNum, int colNum){
        this.name = name;
        this.file = file;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.matrix = new BigInteger[rowNum][colNum];
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                this.matrix[i][j] = BigInteger.ZERO;
            }
        }

    }
    @Override
    public BigInteger getElement(int row, int col) {
        return matrix[row][col];
    }

    @Override
    public void putElement(int row, int col, BigInteger value) {
        matrix[row][col] = value;
    }

    @Override
    public void printMatrix() {
        System.out.println("Matrix: " + name);
        for(int i  = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                System.out.println(i + "," + j + " = " + matrix[i][j]);
            }
        }
    }
}
