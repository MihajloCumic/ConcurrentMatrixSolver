package cli;

import java.io.IOException;

public class CommandRunnerTest {
    public static void main(String[] args) {
        CommandLineRunner cli = new CommandLineRunner(null, null);
        try {
            cli.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
