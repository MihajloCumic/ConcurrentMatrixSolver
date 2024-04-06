package matrix.impl;

import logger.GlobalLogger;
import matrix.Matrix;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class MatrixImpl implements Matrix {
    private  String name;
    private Path file;
    private int rowNum;
    private int colNum;

    private final BigInteger[][] matrix;

    public MatrixImpl(String name, int rowNum, int colNum){
        this.name = name;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.file = null;
        this.matrix = createZeroMatrix();
    }

    public MatrixImpl(Path file){
        initializeMatrixProperties(file);
        this.file = file;
        this.matrix = createZeroMatrix();
    }

    private BigInteger[][] createZeroMatrix(){
        BigInteger[][] matrix =new BigInteger[rowNum][colNum];
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                matrix[i][j] = BigInteger.ZERO;
            }
        }
        return matrix;
    }

    private void initializeMatrixProperties(Path file){
        //matrix_name=A2C2, rows=127, cols=117
        String firstLine = "";
        try(Stream<String> lineStream = Files.lines(file)) {
            firstLine = lineStream.limit(1).toList().get(0);
        } catch (IOException e) {
            GlobalLogger.getInstance().logError(e.getLocalizedMessage());
        }
        try {
            String[] parts = firstLine.split(",");
            name = parts[0].trim().split("=")[1];
            rowNum = Integer.parseInt(parts[1].trim().split("=")[1]);
            colNum = Integer.parseInt(parts[2].trim().split("=")[1]);
        }catch (Exception e){
            GlobalLogger.getInstance().logError(e.getLocalizedMessage());
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
    public void addMatrix(Matrix matrix) {
        if(matrix.getRowNumber() != rowNum && matrix.getColNumber() != colNum) return;
        for(int i = 0; i < rowNum; i++){
            for(int j = 0; j < colNum; j++){
                this.matrix[i][j] = this.matrix[i][j].add(matrix.getElement(i, j));
            }
        }
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
    public String getName() {
        return name;
    }

    @Override
    public String getFilePath() {
        if(file == null) return "No file.";
        return file.toString();
    }

    @Override
    public String getMatrixContentAsString() {
//        matrix_name=A1C1, rows=146, cols=146
//        0,0 = 7129160
        StringBuilder sb = new StringBuilder();
        sb.append("matrix_name=").append(name).append(", rows=").append(rowNum).append(", cols=").append(colNum).append("\n");
        for(int i = 0; i < colNum; i++){
            for(int j = 0; j < rowNum; j++){
                sb.append(j).append(",").append(i).append(" = ").append(matrix[j][i]).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public void printMatrix(boolean format) {
        System.out.println("Matrix: " + name);
        for(int i  = 0; i < colNum; i++){
            if(format) System.out.println();
            for(int j = 0; j < rowNum; j++){
                if(format){
                    System.out.print(matrix[j][i] + " ");
                    continue;
                }
                System.out.println(j + "," + i + " = " + matrix[j][i]);
            }
        }
        if (format) System.out.println();
    }

    @Override
    public void setFile(Path file) {
        this.file = file;
    }

    public BigInteger[][] getMatrix() {
        return matrix;
    }
}
