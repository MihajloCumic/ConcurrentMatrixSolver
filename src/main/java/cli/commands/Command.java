package cli.commands;


import brain.MatrixBrain;
import cli.commands.impl.InfoCommand;
import cli.commands.impl.MultiplyCommand;
import cli.commands.impl.SaveCommand;
import cli.commands.impl.StopCommand;

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

    public static Command newStopCommand(MatrixBrain matrixBrain){
        return new StopCommand(matrixBrain);
    }
    public static Command newSaveCommand(MatrixBrain matrixBrain){return new SaveCommand(matrixBrain);
    }

}
