package system.explorer.finder;

import queue.TaskQueue;
import system.explorer.creator.TaskCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileFinder {

    private final Path starterPath;
    private final String extensionToFind;
    private final TaskCreator taskCreator;


    public FileFinder(String starterPath, String extensionToFind, TaskCreator taskCreator){
        this.starterPath = Path.of(starterPath);
        this.extensionToFind = extensionToFind;
        this.taskCreator = taskCreator;
    }

    public void findFiles(){
        try(Stream<Path> filePath = Files.walk(starterPath)) {
             filePath
                    .filter(path -> {
                        boolean isDirectory = Files.isDirectory(path);
                        if(isDirectory) System.out.println("Searching folder: " + path.getFileName());
                        return !isDirectory;
                    })
                    .filter(path -> path.toString().toLowerCase().endsWith(extensionToFind))
                    .forEach(path -> {
                            System.out.println("Found file: " + path.getFileName());
                            taskCreator.createAndSendTask(path);
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
