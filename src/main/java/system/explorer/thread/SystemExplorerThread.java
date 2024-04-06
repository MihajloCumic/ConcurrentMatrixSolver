package system.explorer.thread;

import system.explorer.SystemExplorer;
import system.explorer.finder.FileFinder;

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
        System.out.println("System Explorer stopped.");
    }

    @Override
    public void addPath(String path) throws Exception {
        System.out.println("Adding new dir.");
        paths.add(Path.of(path));
    }

    @Override
    public void stop(){
        run = false;
    }
}
