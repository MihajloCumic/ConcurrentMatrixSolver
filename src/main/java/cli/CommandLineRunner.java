package cli;

import brain.MatrixBrain;
import cli.commands.Command;
import cli.commands.impl.InfoCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineRunner implements Runner{

    private final BufferedReader reader;
    private final MatrixBrain matrixBrain;


    public CommandLineRunner(MatrixBrain matrixBrain){
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.matrixBrain = matrixBrain;

    }

    @Override
    public void run() throws IOException {
        while(true){
            String input = reader.readLine();
            String[] parts = input.split(" ");
            if(parts.length == 0){
                System.out.println("No command.");
                continue;
            }
            String command = parts[0];
            if(command.equals("shutdown")){
                Command.newStopCommand(matrixBrain).execute(input);
                continue;
            }
            if(command.equals("stop")){
                break;
            }
            if(command.equals("info")){
                Command.newInfoCommand(matrixBrain).execute(input);
                continue;
            }
            if(command.equals("multiply")){
                Command.newMultiplyCommand(matrixBrain).execute(input);
                continue;
            }
            if(command.equals("save")){
                Command.newSaveCommand(matrixBrain).execute(input);
                continue;
            }
            if(command.equals("clear")){
                Command.newClearCommand(matrixBrain).execute(input);
                continue;
            }
            System.out.println("Invalid command: " + input);
        }
        System.out.println("Finished.");
    }

}
