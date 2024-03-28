package cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLIneRunner {

    private final BufferedReader reader;

    public CommandLIneRunner(){
        this.reader = new BufferedReader(new InputStreamReader(System.in));
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
                continue;
            }
        }
        System.out.println("Finished.");
    }

}
