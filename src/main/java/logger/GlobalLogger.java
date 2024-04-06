package logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalLogger {
    private static GlobalLogger instance;
    private final Logger logger;

    private GlobalLogger() {
        this.logger = Logger.getLogger(GlobalLogger.class.getName());
        logger.setLevel(Level.INFO);
    }

    public static synchronized GlobalLogger getInstance(){
        if(instance == null) instance = new GlobalLogger();
        return instance;
    }

    public void logInfo(String message){
        logger.info(message);
    }

    public void logError(String message){
        logger.severe(message);
    }
}
