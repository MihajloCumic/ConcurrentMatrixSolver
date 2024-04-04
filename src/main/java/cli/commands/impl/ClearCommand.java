package cli.commands.impl;

import brain.MatrixBrain;
import cli.commands.Command;

public class ClearCommand extends Command {

    public ClearCommand(MatrixBrain matrixBrain) {
        super(matrixBrain);
    }

    @Override
    protected void execute(String[] tokens) {
        if(tokens.length != 2){
            System.out.println("Invalid input.");
            return;
        }
        String matrixName = tokens[1].endsWith(".rix") ? tokens[1].replaceAll(".rix", "") : tokens[1];
        matrixBrain.clearMatrix(matrixName);
    }
}
