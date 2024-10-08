package task.impl;

import extractor.thread.ExtractorWorker;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import task.Task;
import task.type.TaskType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class CreateMatrixTask implements Task<Matrix> {
    private static final TaskType TASK_TYPE = TaskType.CREATE;
    private final Path potentialMatrixFile;

    public CreateMatrixTask(Path potentialMatrixFile){
        this.potentialMatrixFile = potentialMatrixFile;
    }
    @Override
    public TaskType getType() {
        return TASK_TYPE;
    }

    @Override
    public Future<Matrix> initiate() {
        return null;
    }

    public Path getPotentialMatrixFile() {
        return potentialMatrixFile;
    }
}
