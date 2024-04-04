package system.explorer;

import queue.TaskQueue;
import system.explorer.cache.FileCache;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;
import system.explorer.thread.SystemExplorerThread;

public abstract class SystemExplorer implements Runnable{
    public abstract void stop();

    public static SystemExplorer newSystemExplorer(String starterPath,TaskQueue taskQueue, long pause){
        FileFinder fileFinder = new FileFinder(starterPath, ".rix", new TaskCreator(taskQueue), new FileCache());
        return new SystemExplorerThread(fileFinder, pause);
    }

}
