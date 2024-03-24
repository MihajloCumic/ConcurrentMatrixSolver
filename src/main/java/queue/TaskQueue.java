package queue;

import task.Task;

public interface TaskQueue {
    void addTask(Task task);
    Task takeTask();
}
