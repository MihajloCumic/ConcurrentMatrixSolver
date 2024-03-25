package extractor.thread;

import java.nio.file.Path;
import java.util.concurrent.RecursiveAction;

public class ExtractorWorker extends RecursiveAction {
    private final int LIMIT;
    private int start;
    private int end;

    public ExtractorWorker(int LIMIT, Path file){
        this.LIMIT = LIMIT;
    }
    @Override
    protected void compute() {

    }
}
