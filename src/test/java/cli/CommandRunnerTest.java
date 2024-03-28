package cli;

import java.io.IOException;

public class CommandRunnerTest {
    public static void main(String[] args) {
        CommandLIneRunner cli = new CommandLIneRunner();
        try {
            cli.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
