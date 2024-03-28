package cli.commands;

import brain.MatrixBrain;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MultiplyCommand {
    private final MatrixBrain matrixBrain;

    public MultiplyCommand(MatrixBrain matrixBrain){
        this.matrixBrain = matrixBrain;
    }


    public String execute(String input){
       String[] commaSplit = input.split(",");
       if(commaSplit.length != 2) return "Invalid input.";
       String firstMatrixName = commaSplit[0].trim().split(" ")[1];
       String[] parts = commaSplit[1].trim().split(" ");
       if(parts.length == 0) return "Invalid input";
       String secondMatrixName = parts[0];
       String resultMatrixName = null;
       if(parts.length == 3){
           if(parts[1].equals("-name")){
               resultMatrixName = parts[2];
           }
       }
        try {
            return matrixBrain.multiplyMatricesBlocking(firstMatrixName, secondMatrixName, resultMatrixName).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
