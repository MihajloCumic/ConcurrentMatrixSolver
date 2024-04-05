package cli.commands.impl;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import system.explorer.SystemExplorer;

public class StopCommand extends Command {
    private final SystemExplorer systemExplorer;
    public StopCommand(MatrixBrain matrixBrain, SystemExplorer systemExplorer){

        super(matrixBrain);
        this.systemExplorer = systemExplorer;
    }

    @Override
    protected void execute(String[] tokens) {
        matrixBrain.shutdown();
        systemExplorer.stop();
    }
}
