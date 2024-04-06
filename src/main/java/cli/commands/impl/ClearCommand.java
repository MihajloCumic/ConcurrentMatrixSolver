package cli.commands.impl;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import logger.GlobalLogger;

public class ClearCommand extends Command {

    public ClearCommand(MatrixBrain matrixBrain) {
        super(matrixBrain);
    }

    @Override
    protected void execute(String[] tokens) {
        if(tokens.length != 2){
            GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
            return;
        }
        String matrixName = tokens[1].endsWith(".rix") ? tokens[1].replaceAll(".rix", "") : tokens[1];
        matrixBrain.clearMatrix(matrixName);
    }
}
