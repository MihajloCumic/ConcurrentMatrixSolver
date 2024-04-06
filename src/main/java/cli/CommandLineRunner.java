package cli;

import brain.MatrixBrain;
import brain.pool.MatrixBrainPool;
import cli.commands.Command;
import logger.GlobalLogger;
import system.explorer.SystemExplorer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineRunner implements Runner{

    private final BufferedReader reader;
    private final MatrixBrain matrixBrain;
    private final SystemExplorer systemExplorer;


    public CommandLineRunner(MatrixBrain matrixBrain, SystemExplorer systemExplorer){
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.matrixBrain = matrixBrain;
        this.systemExplorer = systemExplorer;

    }

    @Override
    public void run() throws IOException {
        while(true){
            String input = reader.readLine();
            String[] parts = input.split(" ");
            if(parts.length == 0){
                GlobalLogger.getInstance().logError("No command.");
                continue;
            }
            String command = parts[0];
            if(command.equals("stop")){
                Command.newStopCommand(matrixBrain, systemExplorer).execute(input);
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
                Command.newClearCommand(matrixBrain, systemExplorer).execute(input);
                continue;
            }
            if(command.equals("dir")){
                Command.newDirCommand(matrixBrain, systemExplorer).execute(input);
                continue;
            }
            GlobalLogger.getInstance().logError("Command " + parts[0] + "does not exist.");
        }
    }

}
