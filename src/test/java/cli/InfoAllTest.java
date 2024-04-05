package cli;

import brain.pool.MatrixBrainPool;
import coordinator.delegator.TaskCoordinator;
import coordinator.thread.CoordinatorThread;
import extractor.pool.ExtractorPool;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import multiplier.pool.MultiplierPool;
import queue.TaskQueue;
import queue.impl.TaskQueueImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class InfoAllTest {
    public static void main(String[] args) {
        Matrix m1 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica1.rix"));
        Matrix m2 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica2.rix"));
        Matrix m3 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica3.rix"));
        Matrix m4 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica4.rix"));

        TaskQueue taskQueue = new TaskQueueImpl();
        MatrixBrainPool matrixBrainPool = new MatrixBrainPool(Executors.newCachedThreadPool(), taskQueue);
        TaskCoordinator taskCoordinator = new TaskCoordinator(new ExtractorPool(matrixBrainPool, 1024), new MultiplierPool(matrixBrainPool, 10, false));
        Thread coordinator = new Thread(new CoordinatorThread(taskCoordinator, taskQueue));
        coordinator.start();

        matrixBrainPool.cacheMatrix(m1);
        matrixBrainPool.cacheMatrix(m2);
        matrixBrainPool.cacheMatrix(m3);
        matrixBrainPool.cacheMatrix(m4);
        CommandLineRunner cli = new CommandLineRunner(matrixBrainPool);
        try {
            cli.run();
            coordinator.join();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
