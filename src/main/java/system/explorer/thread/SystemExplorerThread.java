package system.explorer.thread;

import system.explorer.finder.FileFinder;

public class SystemExplorerThread implements Runnable{
    private final FileFinder fileFinder;


    public SystemExplorerThread(FileFinder fileFinder){
        this.fileFinder = fileFinder;
    }
    @Override
    public void run() {
        while (true){
            fileFinder.findFiles();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
