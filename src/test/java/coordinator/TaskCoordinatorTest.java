package coordinator;

import coordinator.impl.TaskCoordinator;
import coordinator.thread.CoordinatorThread;
import extractor.pool.ExtractorPool;
import queue.TaskQueue;
import queue.impl.TaskQueueImpl;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;
import system.explorer.thread.SystemExplorerThread;

public class TaskCoordinatorTest {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueueImpl();
        TaskCreator taskCreator = new TaskCreator(taskQueue);
        FileFinder fileFinder = new FileFinder("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test", ".rix", taskCreator, null);
//        fileFinder.findFiles();
        Thread systemExplorer = new Thread(new SystemExplorerThread(fileFinder));

        Coordinator coordinator = new TaskCoordinator(new ExtractorPool());
        Thread coordinatorThread = new Thread(new CoordinatorThread(coordinator, taskQueue));

        systemExplorer.start();
        coordinatorThread.start();

        try {
            systemExplorer.join();
            coordinatorThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("Finished");
        }

    }
}
