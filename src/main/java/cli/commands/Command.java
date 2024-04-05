package cli.commands;


import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.impl.*;
import system.explorer.SystemExplorer;

public abstract class Command {

    protected MatrixBrain matrixBrain;

    public Command(MatrixBrain matrixBrain){
        this.matrixBrain = matrixBrain;
    }

    public void execute(String input){
        execute(extractTokens(input));
    }

    protected abstract void execute(String[] tokens);

    private String[] extractTokens(String input){
        return input.replace(",", " ").replaceAll("[^\\S\\r\\n]{2,}", " ").trim().split(" ");
    }

    public static Command newInfoCommand(MatrixBrain matrixBrain){
        return new InfoCommand(matrixBrain);
    }
    public static Command newMultiplyCommand(MatrixBrain matrixBrain){
        return new MultiplyCommand(matrixBrain);
    }

    public static Command newStopCommand(MatrixBrain matrixBrain, SystemExplorer systemExplorer){
        return new StopCommand(matrixBrain, systemExplorer);
    }
    public static Command newSaveCommand(MatrixBrain matrixBrain){return new SaveCommand(matrixBrain);
    }
    public static Command newClearCommand(MatrixBrain matrixBrain){return new ClearCommand(matrixBrain);
    }

}
