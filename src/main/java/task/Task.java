package task;

import task.type.TaskType;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface Task<T> {
    TaskType getType();
    Future<T> initiate();
}
