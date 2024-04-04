package app;

import brain.MatrixBrain;
import cli.CommandLineRunner;
import cli.Runner;
import coordinator.Coordinator;
import extractor.Extractor;
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
            Thread systemExplorer = new Thread(SystemExplorer.newSystemExplorer(configProperties.startDir(), taskQueue, configProperties.sleepTime()));
            coordinator.start();
            systemExplorer.start();
            Runner commandRunner = new CommandLineRunner(matrixBrain);
            commandRunner.run();

            coordinator.join();
            systemExplorer.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
