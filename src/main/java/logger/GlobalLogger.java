package logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GlobalLogger {
    private static GlobalLogger instance;
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";


    private GlobalLogger() {
    }

    public static synchronized GlobalLogger getInstance(){
        if(instance == null) instance = new GlobalLogger();
        return instance;
    }

    public void logInfo(String message){
        System.out.println(message);
    }

    public void logError(String message){
        System.out.println(ANSI_RED + "Error:" + ANSI_RESET + " " + message);
    }
}
