package matrix.impl;

import matrix.Matrix;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class MatrixImpl implements Matrix {
    private  String name;
    private final Path file;
    private int rowNum;
    private int colNum;

    private final BigInteger[][] matrix;

    public MatrixImpl(Path file){
        initializeMatrixProperties(file);
        this.file = file;
        this.matrix = new BigInteger[rowNum][colNum];
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                this.matrix[i][j] = BigInteger.ZERO;
            }
        }

    }

    private void initializeMatrixProperties(Path file){
        //matrix_name=A2C2, rows=127, cols=117
        String firstLine = "";
        try(Stream<String> lineStream = Files.lines(file)) {
            firstLine = lineStream.limit(1).toList().get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] parts = firstLine.split(",");
        name = parts[0].trim().split("=")[1];
        rowNum = Integer.parseInt(parts[1].trim().split("=")[1]);
        colNum = Integer.parseInt(parts[2].trim().split("=")[1]);

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
    public int getRowNumber() {
        return rowNum;
    }

    @Override
    public int getColNumber() {
        return colNum;
    }

    @Override
    public void printMatrix(boolean format) {
        System.out.println("Matrix: " + name);
        for(int i  = 0; i < rowNum; i++){
            if(format) System.out.println();
            for(int j = 0; j < colNum; j++){
                if(format){
                    System.out.print(matrix[i][j] + " ");
                    continue;
                }
                System.out.println(i + "," + j + " = " + matrix[i][j]);
            }
        }
        if (format) System.out.println();
    }
}
