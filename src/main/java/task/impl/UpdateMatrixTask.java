package task.impl;

import task.Task;
import task.type.TaskType;

import java.nio.file.Path;
import java.util.concurrent.Future;

public class UpdateMatrixTask implements Task {
    private static final TaskType TASK_TYPE = TaskType.UPDATE;
    private final Path matrixFile;

    public UpdateMatrixTask(Path matrixFile){
        this.matrixFile = matrixFile;
    }
    @Override
    public TaskType getType() {
        return TASK_TYPE;
    }

    @Override
    public Future<?> initiate() {
        return null;
    }

    public Path getMatrixFile() {
        return matrixFile;
    }
}
