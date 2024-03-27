package queue.impl;

import queue.TaskQueue;
import task.Task;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueueImpl implements TaskQueue {
    private final LinkedBlockingQueue<Task> taskQueue;

    public TaskQueueImpl(){
        this.taskQueue = new LinkedBlockingQueue<>();
    }


    @Override
    public void addTask(Task task) {
        taskQueue.add(task);
    }

    @Override
    public Task takeTask() {
        try {
            return taskQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

}
