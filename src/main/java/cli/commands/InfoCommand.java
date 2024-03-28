package cli.commands;

import brain.MatrixBrain;
import matrix.Matrix;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class InfoCommand {
    private final MatrixBrain matrixBrain;
    public InfoCommand(MatrixBrain matrixBrain){
        this.matrixBrain = matrixBrain;
    }
    public void execute(){
        try {
            List<Matrix> matrices = matrixBrain.getMatrixInfo().get();
            for(Matrix matrix: matrices){
                System.out.println("Matrix: " + matrix.getName() + ",rows= " + matrix.getRowNumber() + ",cols= " + matrix.getColNumber());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
