package system.explorer.thread;

import system.explorer.SystemExplorer;
import system.explorer.finder.FileFinder;

public class SystemExplorerThread extends SystemExplorer {
    private final FileFinder fileFinder;
    private final long pause;
    private volatile boolean run = true;


    public SystemExplorerThread(FileFinder fileFinder, long pause){
        this.fileFinder = fileFinder;
        this.pause = pause;
    }
    @Override
    public void run() {
        while (run){
            fileFinder.findFiles();
            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("System Explorer stopped.");
    }

    @Override
    public void stop(){
        run = false;
    }
}
