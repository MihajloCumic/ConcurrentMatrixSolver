package result.impl;

import matrix.Matrix;
import result.Result;

import java.util.ArrayList;
import java.util.List;

public class InfoResult implements Result {
    private final List<Matrix> matrices;

    public InfoResult(){
        this.matrices = new ArrayList<>();
    }

    public List<Matrix> getMatrices() {
        return matrices;
    }

    @Override
    public String resultAsString() {
        //‘A | rows = 1000, cols = 1000 | matrix_file.rix’
        StringBuilder sb = new StringBuilder();
        for(Matrix matrix: matrices){
            sb.append(matrix.getName())
                    .append(" | rows = ")
                    .append(matrix.getRowNumber())
                    .append(", cols = ")
                    .append(matrix.getColNumber())
                    .append(" | ")
                    .append(matrix.getFilePath())
                    .append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean resultContainsMatrix(Matrix matrix) {
        for(Matrix m: matrices){
            if(m.getName().equals(matrix.getName())) return true;
        }
        return false;
    }
}
