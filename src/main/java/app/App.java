package app;

import brain.MatrixBrain;
import cli.CommandLineRunner;
import cli.Runner;
import coordinator.Coordinator;
import extractor.Extractor;
import logger.GlobalLogger;
import multiplier.Multiplier;
import properties.Loader;
import properties.impl.PropertiesLoader;
import properties.model.ConfigProperties;
import queue.TaskQueue;
import queue.impl.TaskQueueImpl;
import system.explorer.SystemExplorer;

public class App {
    public static void main(String[] args) {
        Loader loader = new PropertiesLoader("src/main/resources/app.properties");
        try {
            ConfigProperties configProperties = loader.load();
            TaskQueue taskQueue = new TaskQueueImpl();
            MatrixBrain matrixBrain = MatrixBrain.newMatrixBrain(taskQueue);
            Multiplier multiplier = Multiplier.newMatrixMultiplier(matrixBrain, configProperties.maxRowsSize());
            Extractor extractor = Extractor.newMatrixExtractor(matrixBrain, configProperties.maxChunkSize());
            Thread coordinator = new Thread(Coordinator.newTaskCoordinator(taskQueue, extractor, multiplier));
            SystemExplorer systemExplorer = SystemExplorer.newSystemExplorer(configProperties.startDir(), taskQueue, configProperties.sleepTime());
            Thread systemExplorerThread = new Thread(systemExplorer);
            coordinator.start();
            systemExplorerThread.start();
            Runner commandRunner = new CommandLineRunner(matrixBrain, systemExplorer);
            commandRunner.run();

            coordinator.join();
            systemExplorerThread.join();
            GlobalLogger.getInstance().logInfo("Finished");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
