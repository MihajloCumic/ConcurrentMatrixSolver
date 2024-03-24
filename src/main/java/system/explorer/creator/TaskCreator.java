package system.explorer.creator;

import task.Task;
import task.impl.CreateMatrixTask;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskCreator {
    private final List<Path> files;
    public TaskCreator(){
        this.files = new ArrayList<>();
    }

    public void createAndSendTask(Path file){
        files.add(file);
        Task task = new CreateMatrixTask(file);
    }

    public List<Path> getFiles() {
        return files;
    }
}
