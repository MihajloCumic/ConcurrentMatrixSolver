package cli;

import brain.MatrixBrain;
import cli.commands.InfoCommand;
import cli.commands.MultiplyCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLIneRunner {

    private final BufferedReader reader;
    private final InfoCommand infoCommand;
    private final MultiplyCommand multiplyCommand;

    public CommandLIneRunner(MatrixBrain matrixBrain){
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.infoCommand = new InfoCommand(matrixBrain);
        this.multiplyCommand = new MultiplyCommand(matrixBrain);
    }

    public void run() throws IOException {
        while(true){
            String input = reader.readLine();
            String[] parts = input.split(" ");
            if(parts.length == 0){
                System.out.println("No command.");
                continue;
            }
            String command = parts[0];
            if(command.equals("stop")){
                System.out.println("Stoping...");
                break;
            }
            if(command.equals("info")){
                System.out.println("Info.");
                infoCommand.execute();
                continue;
            }
            if(command.equals("multiply")){
                System.out.println("Multiply.");
                String result = multiplyCommand.execute(input);
                System.out.println(result);
                continue;
            }
            System.out.println("Invalid command: " + input);
        }
        System.out.println("Finished.");
    }

}
