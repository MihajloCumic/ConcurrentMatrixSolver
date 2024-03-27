package extractor;

import queue.TaskQueue;
import queue.impl.TaskQueueImpl;
import system.explorer.cache.FileCache;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;
import system.explorer.thread.SystemExplorerThread;

public class FIleFInderCacheTest {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueueImpl();
        TaskCreator taskCreator = new TaskCreator(taskQueue);
        FileCache fileCache = new FileCache();
        FileFinder fileFinder = new FileFinder("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test", ".rix", taskCreator, fileCache);
        Thread thread1 = new Thread(new SystemExplorerThread(fileFinder));
        Thread thread2 = new Thread(new SystemExplorerThread(fileFinder));

        thread1.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
