package cli.commands.impl;

import brain.MatrixBrain;

public class StopCommand {
    private final MatrixBrain matrixBrain;

    public StopCommand(MatrixBrain matrixBrain){
        this.matrixBrain = matrixBrain;
    }

    public String execute(){

        return "Stopping the app";
    }
}
