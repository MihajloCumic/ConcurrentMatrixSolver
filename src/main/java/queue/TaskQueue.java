package queue;

import task.Task;

public interface TaskQueue {
    void addTask(Task task);

    boolean isEmpty();
    Task takeTask();
}
