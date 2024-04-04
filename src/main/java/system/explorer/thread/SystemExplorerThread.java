package system.explorer.thread;

import system.explorer.finder.FileFinder;

public class SystemExplorerThread implements Runnable{
    private final FileFinder fileFinder;
    private final long pause;
    private boolean run = true;


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
    }

    public void stop(){
        run = false;
    }
}
