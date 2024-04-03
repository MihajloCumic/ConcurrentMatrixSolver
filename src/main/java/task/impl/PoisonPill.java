package task.impl;

import task.Task;
import task.type.TaskType;

import java.util.concurrent.Future;

public class PoisonPill implements Task {
    @Override
    public TaskType getType() {
        return TaskType.POISON_PILL;
    }

    @Override
    public Future initiate() {
        return null;
    }
}
