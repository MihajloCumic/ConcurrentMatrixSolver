package task.impl;

import task.Task;
import task.type.TaskType;

import java.nio.file.Path;
import java.util.concurrent.Future;

public class CreateMatrixTask implements Task {
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
    public Future<?> initiate() {
        return null;
    }
}
