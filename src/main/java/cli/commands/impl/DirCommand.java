package cli.commands.impl;

import brain.MatrixBrain;
import cli.commands.Command;
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
            System.out.println("Invalid input.");
            return;
        }
        try {
            System.out.println("Callint with: " + tokens[1]);
            systemExplorer.addPath(tokens[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
