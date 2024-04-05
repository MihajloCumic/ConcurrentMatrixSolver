package cli.commands.impl;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import matrix.Matrix;
import result.Result;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class InfoCommand extends Command {

    public InfoCommand(MatrixBrain matrixBrain){
        super(matrixBrain);
    }
    @Override
    protected void execute(String[] tokens) {
        if(tokens.length == 2){
            if(tokens[1].equals("-all")) matrixBrain.getAllMatricesInfo();
            else matrixBrain.getMatrixInfo(tokens[1]);
            return;
        }
        if(tokens.length == 3){
            return;
        }
        System.out.println("Invalid command.");

    }

}
