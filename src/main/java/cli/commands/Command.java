package cli.commands;


import brain.pool.MatrixBrainPool;
import cli.commands.impl.*;

public abstract class Command {

    protected MatrixBrainPool matrixBrainPool;

    public Command(MatrixBrainPool matrixBrainPool){
        this.matrixBrainPool = matrixBrainPool;
    }

    public void execute(String input){
        execute(extractTokens(input));
    }

    protected abstract void execute(String[] tokens);

    private String[] extractTokens(String input){
        return input.replace(",", " ").replaceAll("[^\\S\\r\\n]{2,}", " ").trim().split(" ");
    }

    public static Command newInfoCommand(MatrixBrainPool matrixBrainPool){
        return new InfoCommand(matrixBrainPool);
    }
    public static Command newMultiplyCommand(MatrixBrainPool matrixBrainPool){
        return new MultiplyCommand(matrixBrainPool);
    }

    public static Command newStopCommand(MatrixBrainPool matrixBrainPool){
        return new StopCommand(matrixBrainPool);
    }
    public static Command newSaveCommand(MatrixBrainPool matrixBrainPool){return new SaveCommand(matrixBrainPool);
    }
    public static Command newClearCommand(MatrixBrainPool matrixBrainPool){return new ClearCommand(matrixBrainPool);
    }

}
