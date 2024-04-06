package system.explorer;

import queue.TaskQueue;
import system.explorer.cache.FileCache;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;
import system.explorer.thread.SystemExplorerThread;

import java.nio.file.Path;

public abstract class SystemExplorer implements Runnable{
    public abstract void stop();
    public abstract void addPath(String path) throws Exception;
    public abstract void removePath(String path);

    public static SystemExplorer newSystemExplorer(String starterPath,TaskQueue taskQueue, long pause) throws Exception{
        FileFinder fileFinder = new FileFinder(".rix", new TaskCreator(taskQueue), new FileCache());
        return new SystemExplorerThread(Path.of(starterPath), fileFinder, pause);
    }

}
