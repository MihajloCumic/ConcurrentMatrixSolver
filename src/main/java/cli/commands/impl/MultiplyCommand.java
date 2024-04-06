package cli.commands.impl;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import logger.GlobalLogger;
import result.Result;

import java.util.concurrent.ExecutionException;

public class MultiplyCommand extends Command {

    public MultiplyCommand(MatrixBrain matrixBrain){
        super(matrixBrain);
    }
    @Override
    protected void execute(String[] tokens) {
        if(tokens.length < 3 || tokens.length > 6) {
            GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
            return;
        }
        boolean async = false;
        String firstMatrixName = tokens[1];
        String secondMatrixName = tokens[2];
        String resultMatrixName = "";
        if(tokens.length == 5){
            if(tokens[3].equals("-name")){
                resultMatrixName = tokens[4];
            }else {
                GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
                return;
            }
        }
        if(tokens.length == 4){
            if(tokens[3].equals("-async")){
                async = true;
            }else {
                GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
                return;
            }
        }
        if(tokens.length == 6){
            if(tokens[3].equals("-name")){
                resultMatrixName = tokens[4];
            }else {
                GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
                return;
            }
            if(tokens[5].equals("-async")){
                async = true;
            }else {
                GlobalLogger.getInstance().logError("Invalid input for command " + tokens[0]);
                return;
            }
        }
        if(resultMatrixName.isEmpty() || resultMatrixName.isBlank()) resultMatrixName = firstMatrixName.concat(secondMatrixName);
        if(async){
            matrixBrain.multiplyMatricesAsync(firstMatrixName, secondMatrixName, resultMatrixName);
            return;
        }
        matrixBrain.multiplyMatricesBlocking(firstMatrixName, secondMatrixName, resultMatrixName);


    }
}
