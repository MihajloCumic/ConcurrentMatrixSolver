package system.explorer.finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FileFinder {

    private final Path starterPath;
    private final String extensionToFind;
    private final Map<Path, FileTime> lastModified;


    public FileFinder(String starterPath, String extensionToFind){
        this.starterPath = Path.of(starterPath);
        this.extensionToFind = extensionToFind;
        this.lastModified = new HashMap<>();
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
                        try {
                            lastModified.put(path, Files.getLastModifiedTime(path));
                            System.out.println("Found file: " + path.getFileName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
             lastModified.forEach((key1, value1) -> {
                 String key = key1.getFileName().toString();
                 String value = value1.toString();
                 System.out.println("[ " + key + " : " + value + " ] ");
             });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
