package system.explorer.creator;

import queue.TaskQueue;
import task.Task;
import task.impl.CreateMatrixTask;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskCreator {
    private final List<Path> files;
    private final TaskQueue taskQueue;
    public TaskCreator(TaskQueue taskQueue){
        this.files = new ArrayList<>();
        this.taskQueue = taskQueue;
    }

    public void createAndSendTask(Path file){
        files.add(file);
        Task task = new CreateMatrixTask(file);
        taskQueue.addTask(task);
    }

    public List<Path> getFiles() {
        return files;
    }
}
