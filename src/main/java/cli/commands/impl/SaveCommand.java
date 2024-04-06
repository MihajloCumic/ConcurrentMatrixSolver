package cli.commands.impl;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import logger.GlobalLogger;

import java.nio.file.Path;

public class SaveCommand extends Command {

    public SaveCommand(MatrixBrain matrixBrain) {
        super(matrixBrain);
    }

    @Override
    protected void execute(String[] tokens) {
        if(tokens.length != 5){
            GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
            return;
        }
        if(tokens[1].equals("-name") && tokens[3].equals("-file")){
            String matrixName = tokens[2];
            Path file = Path.of(tokens[4]);
            matrixBrain.saveMatrixInFile(matrixName, file);
            return;
        }
        GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
    }
}
