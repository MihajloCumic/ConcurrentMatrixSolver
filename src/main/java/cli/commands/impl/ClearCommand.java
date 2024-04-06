package cli.commands.impl;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import logger.GlobalLogger;
import system.explorer.SystemExplorer;

public class ClearCommand extends Command {
    private final SystemExplorer systemExplorer;
    public ClearCommand(MatrixBrain matrixBrain, SystemExplorer systemExplorer) {
        super(matrixBrain);
        this.systemExplorer = systemExplorer;
    }

    @Override
    protected void execute(String[] tokens) {
        if(tokens.length != 2){
            GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
            return;
        }
        if(tokens[1].endsWith(".rix")){
            matrixBrain.clearMatrix(tokens[1], systemExplorer, false);
            return;
        }
        matrixBrain.clearMatrix(tokens[1], systemExplorer, true);
    }
}
