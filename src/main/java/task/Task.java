package task;

import task.type.TaskType;

import java.util.concurrent.Future;

public interface Task {
    TaskType getType();
    Future<?> initiate();
}
