package finder;

import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FIleFinderTest {
    public static void main(String[] args) {
        TaskCreator taskCreator = new TaskCreator();
        FileFinder fileFinder = new FileFinder("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test", ".rix", taskCreator);
        fileFinder.findFiles();
        List<Path> files = taskCreator.getFiles();

        try {
            BufferedReader bufferedReader = Files.newBufferedReader(files.get(0));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Path file: files){
            try(Stream<String> fileStream = Files.lines(file)) {
                System.out.println((int)fileStream.count());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
