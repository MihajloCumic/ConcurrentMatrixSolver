package cli;

import brain.pool.MatrixBrainPool;
import cli.commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineRunner implements Runner{

    private final BufferedReader reader;
    private final MatrixBrainPool matrixBrainPool;


    public CommandLineRunner(MatrixBrainPool matrixBrainPool){
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.matrixBrainPool = matrixBrainPool;

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
                Command.newStopCommand(matrixBrainPool).execute(input);
                continue;
            }
            if(command.equals("stop")){
                break;
            }
            if(command.equals("info")){
                Command.newInfoCommand(matrixBrainPool).execute(input);
                continue;
            }
            if(command.equals("multiply")){
                Command.newMultiplyCommand(matrixBrainPool).execute(input);
                continue;
            }
            if(command.equals("save")){
                Command.newSaveCommand(matrixBrainPool).execute(input);
                continue;
            }
            if(command.equals("clear")){
                Command.newClearCommand(matrixBrainPool).execute(input);
                continue;
            }
            System.out.println("Invalid command: " + input);
        }
        System.out.println("Finished.");
    }

}
