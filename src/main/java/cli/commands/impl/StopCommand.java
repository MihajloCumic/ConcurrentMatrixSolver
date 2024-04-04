package cli.commands.impl;

import brain.pool.MatrixBrainPool;
import cli.commands.Command;

public class StopCommand extends Command {

    public StopCommand(MatrixBrainPool matrixBrainPool){
        super(matrixBrainPool);
    }

    @Override
    protected void execute(String[] tokens) {
        matrixBrainPool.shutdown();
    }
}
