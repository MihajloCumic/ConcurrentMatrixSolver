package queue.impl;

import queue.TaskQueue;
import task.Task;

import java.util.concurrent.ConcurrentLinkedDeque;

public class TaskQueueImpl implements TaskQueue {
    private final ConcurrentLinkedDeque<Task> taskQueue;

    public TaskQueueImpl(){
        this.taskQueue = new ConcurrentLinkedDeque<>();
    }


    @Override
    public void addTask(Task task) {
        taskQueue.add(task);
    }

    @Override
    public Task takeTask() {
        return taskQueue.pollLast();
    }

    @Override
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public ConcurrentLinkedDeque<Task> getTaskQueue() {
        return taskQueue;
    }
}
