package cli;

import brain.MatrixBrain;
import coordinator.delegator.TaskCoordinator;
import coordinator.thread.CoordinatorThread;
import extractor.pool.ExtractorPool;
import multiplier.pool.MultiplierPool;
import queue.TaskQueue;
import queue.impl.TaskQueueImpl;
import system.explorer.cache.FileCache;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;
import system.explorer.thread.SystemExplorerThread;

import java.io.IOException;
import java.util.concurrent.Executors;

public class CLITest {

    public static void main(String[] args) {

        TaskQueue taskQueue = new TaskQueueImpl();

        MatrixBrain matrixBrain = new MatrixBrain(Executors.newCachedThreadPool(), taskQueue);

        TaskCoordinator taskCoordinator = new TaskCoordinator(new ExtractorPool(matrixBrain, 1024), new MultiplierPool(matrixBrain));
        Thread coordinator = new Thread(new CoordinatorThread(taskCoordinator, taskQueue));
        coordinator.start();

        String starterPath = "/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/folder-1/folder-1-2";
        String extension = ".rix";
        TaskCreator taskCreator = new TaskCreator(taskQueue);
        FileCache fileCache = new FileCache();
        Thread systemExplorer = new Thread(new SystemExplorerThread(new FileFinder(starterPath, extension, taskCreator, fileCache), 5000));
        systemExplorer.start();

        CommandLineRunner cli = new CommandLineRunner(matrixBrain);
        try {
            cli.run();
            systemExplorer.join();
            coordinator.join();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
