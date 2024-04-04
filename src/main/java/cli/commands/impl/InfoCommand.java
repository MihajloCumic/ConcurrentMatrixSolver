package cli.commands.impl;

import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import matrix.Matrix;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class InfoCommand extends Command {

    public InfoCommand(MatrixBrainPool matrixBrainPool){
        super(matrixBrainPool);
    }
    @Override
    protected void execute(String[] tokens) {
        //‘A | rows = 1000, cols = 1000 | matrix_file.rix’
        try {
            List<Matrix> matrices = matrixBrainPool.getMatrixInfo().get();
            for(Matrix matrix: matrices){
                System.out.println(matrix.getName() + " | " + "rows = " + matrix.getRowNumber() + " | cols= " + matrix.getColNumber() + " | " + matrix.getFilePath());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
