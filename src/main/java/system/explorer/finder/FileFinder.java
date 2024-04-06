package system.explorer.finder;

import system.explorer.cache.FileCache;
import system.explorer.creator.TaskCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileFinder {

    private final String extensionToFind;
    private final TaskCreator taskCreator;
    private final FileCache fileCache;


    public FileFinder(String extensionToFind, TaskCreator taskCreator, FileCache fileCache){
        this.extensionToFind = extensionToFind;
        this.taskCreator = taskCreator;
        this.fileCache = fileCache;
    }

    public void findFiles(Path starterPath){
        try(Stream<Path> filePath = Files.walk(starterPath)) {
             filePath
                    .filter(path -> {
                        boolean isDirectory = Files.isDirectory(path);
                        return !isDirectory;
                    })
                    .filter(path -> path.toString().toLowerCase().endsWith(extensionToFind))
                    .forEach(path -> {
                            if(!fileCache.containsKey(path)){
                                System.out.println("Found new file: " + path);
                                fileCache.put(path);
                                taskCreator.createAndSendCreateTask(path);
                                return;
                            }
                            if(fileCache.wasModified(path)){
                                System.out.println("Found modified file: " + path);
                                fileCache.put(path);
                                taskCreator.createAndSendUpdateTask(path);
                            }

                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
