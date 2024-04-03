package cli.commands.impl;

import brain.MatrixBrain;
import cli.commands.Command;

public class StopCommand extends Command {

    public StopCommand(MatrixBrain matrixBrain){
        super(matrixBrain);
    }

    @Override
    protected void execute(String[] tokens) {
        matrixBrain.shutdown();
    }
}
