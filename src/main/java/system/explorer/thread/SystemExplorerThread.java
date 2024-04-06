package system.explorer.thread;

import logger.GlobalLogger;
import system.explorer.SystemExplorer;
import system.explorer.finder.FileFinder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SystemExplorerThread extends SystemExplorer {
    private final FileFinder fileFinder;
    private final long pause;
    private volatile boolean run = true;
    private final List<Path> paths;


    public SystemExplorerThread(Path starterPath, FileFinder fileFinder, long pause){
        this.fileFinder = fileFinder;
        this.pause = pause;
        this.paths = new CopyOnWriteArrayList<>();
        this.paths.add(starterPath);
    }
    @Override
    public void run() {
        while (run){
            Iterator<Path> pathIterator = paths.iterator();
            pathIterator.forEachRemaining(fileFinder::findFiles);
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        GlobalLogger.getInstance().logInfo("SystemExplorer has stopped.");
    }

    @Override
    public void addPath(String path) throws Exception {
        Path dir = Path.of(path);
        if(!Files.exists(dir)){
            GlobalLogger.getInstance().logError("Directory: " + path + " does not exist.");
            GlobalLogger.getInstance().logInfo("Directory: " + path + " does not exist.");
            return;
        }
        GlobalLogger.getInstance().logInfo("Adding new dir: " + path);
        paths.add(dir);
    }

    @Override
    public void stop(){
        run = false;
    }
}
