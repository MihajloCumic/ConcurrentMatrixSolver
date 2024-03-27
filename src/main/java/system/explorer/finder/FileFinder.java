package system.explorer.finder;

import system.explorer.cache.FileCache;
import system.explorer.creator.TaskCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileFinder {

    private final Path starterPath;
    private final String extensionToFind;
    private final TaskCreator taskCreator;
    private final FileCache fileCache;


    public FileFinder(String starterPath, String extensionToFind, TaskCreator taskCreator, FileCache fileCache){
        this.starterPath = Path.of(starterPath);
        this.extensionToFind = extensionToFind;
        this.taskCreator = taskCreator;
        this.fileCache = fileCache;
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
                            if(!fileCache.containsKey(path)){
                                System.out.println("New file.");
                                fileCache.put(path);
                                taskCreator.createAndSendCreateTask(path);
                                return;
                            }
                            if(fileCache.wasModified(path)){
                                System.out.println("Changed file.");
                                fileCache.put(path);
                                taskCreator.createAndSendUpdateTask(path);
                            }

                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
