package cli.commands.impl;

import brain.MatrixBrain;
import cli.CommandLIneRunner;
import cli.commands.Command;
import result.Result;
import result.impl.MultiplyResult;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MultiplyCommand extends Command {

    public MultiplyCommand(MatrixBrain matrixBrain){
        super(matrixBrain);
    }
    @Override
    protected void execute(String[] tokens) {
        if(tokens.length < 3 || tokens.length > 6) System.out.println("Invalid input");
        boolean async = false;
        String firstMatrixName = tokens[1];
        String secondMatrixName = tokens[2];
        String resultMatrixName = "";
        if(tokens.length == 5){
            if(tokens[3].equals("-name")){
                resultMatrixName = tokens[4];
            }else {
                System.out.println("Invalid input.");
                return;
            }
        }
        if(tokens.length == 4){
            if(tokens[3].equals("-async")){
                async = true;
            }else {
                System.out.println("Invalid input.");
                return;
            }
        }
        if(tokens.length == 6){
            if(tokens[3].equals("-name")){
                resultMatrixName = tokens[4];
            }else {
                System.out.println("InvalidInput");
                return;
            }
            if(tokens[5].equals("-async")){
                async = true;
            }else {
                System.out.println("Invalid input");
                return;
            }
        }
        if(resultMatrixName.isEmpty() || resultMatrixName.isBlank()) resultMatrixName = firstMatrixName.concat(secondMatrixName);
        if(async){
            matrixBrain.multiplyMatricesAsync(firstMatrixName, secondMatrixName, resultMatrixName);
            return;
        }
        try {
            Result result = matrixBrain.multiplyMatricesBlocking(firstMatrixName, secondMatrixName, resultMatrixName).get();
            System.out.println(result.toString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }



//    public String execute(String input){
//       String[] commaSplit = input.split(",");
//       if(commaSplit.length != 2) return "Invalid input.";
//       String firstMatrixName = commaSplit[0].trim().split(" ")[1];
//       String[] parts = commaSplit[1].trim().split(" ");
//       if(parts.length == 0) return "Invalid input";
//       String secondMatrixName = parts[0];
//       String resultMatrixName = null;
//       if(parts.length == 3){
//           if(parts[1].equals("-name")){
//               resultMatrixName = parts[2];
//           }
//       }else{
//           resultMatrixName = firstMatrixName + secondMatrixName;
//       }
//        try {
//            System.out.println("Calculating: " + firstMatrixName + " * " + secondMatrixName);
//            Result result =  matrixBrain.multiplyMatricesBlocking(firstMatrixName, secondMatrixName, resultMatrixName).get();
//            return result.toString();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
