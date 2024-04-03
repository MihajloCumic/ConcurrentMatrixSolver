package cli.commands;


import brain.MatrixBrain;

public abstract class Command {

    protected MatrixBrain matrixBrain;

    public static Command commandFactory(String type){
        if(type.equals("info")){
            return null;
        }
        if(type.equals("multiply") || type.equals("mul")){
            return null;
        }
        if(type.equals("stop")){
            return null;
        }
        return null;
    }

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

}
