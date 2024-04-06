package cli.commands.impl;

import brain.MatrixBrain;
import cli.commands.Command;
import logger.GlobalLogger;
import system.explorer.SystemExplorer;

public class DirCommand extends Command {
    private final SystemExplorer systemExplorer;
    public DirCommand(MatrixBrain matrixBrain, SystemExplorer systemExplorer) {
        super(matrixBrain);
        this.systemExplorer = systemExplorer;
    }

    @Override
    protected void execute(String[] tokens) {
        if(tokens.length != 2){
            GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
            return;
        }
        try {
            systemExplorer.addPath(tokens[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
