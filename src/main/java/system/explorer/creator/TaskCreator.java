package system.explorer.creator;

import queue.TaskQueue;
import task.Task;
import task.impl.CreateMatrixTask;
import task.impl.UpdateMatrixTask;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskCreator {
    private final TaskQueue taskQueue;
    public TaskCreator(TaskQueue taskQueue){
        this.taskQueue = taskQueue;
    }

    public void createAndSendUpdateTask(Path file){
        Task task = new UpdateMatrixTask(file);
        taskQueue.addTask(task);
    }

    public void createAndSendCreateTask(Path file){
        Task task = new CreateMatrixTask(file);
        taskQueue.addTask(task);
    }

}
