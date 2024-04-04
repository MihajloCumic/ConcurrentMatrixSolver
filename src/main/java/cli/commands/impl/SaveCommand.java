package cli.commands.impl;

import brain.pool.MatrixBrainPool;
import cli.commands.Command;

import java.nio.file.Path;

public class SaveCommand extends Command {

    public SaveCommand(MatrixBrainPool matrixBrainPool) {
        super(matrixBrainPool);
    }

    @Override
    protected void execute(String[] tokens) {
        if(tokens.length != 5){
            System.out.println("Invalid input.");
            return;
        }
        if(tokens[1].equals("-name") && tokens[3].equals("-file")){
            String matrixName = tokens[2];
            Path file = Path.of(tokens[4]);
            matrixBrainPool.saveMatrixInFile(matrixName, file);
            return;
        }
        System.out.println("Invalid input.");
    }
}
