package finder;

import queue.TaskQueue;
import queue.impl.TaskQueueImpl;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FIleFinderTest {


    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueueImpl();
        TaskCreator taskCreator = new TaskCreator(taskQueue);
        FileFinder fileFinder = new FileFinder("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test", ".rix", taskCreator, null);
        fileFinder.findFiles();
        List<Path> files = taskCreator.getFiles();
        Path file = files.get(0);
        System.out.println(isSizeGood(file));
        try(Stream<String> fileStream = Files.lines(file)) {
            //System.out.println((int)fileStream.count());
//           fileStream.skip(1).limit(5).forEach(line -> {
//               System.out.println(line + " :size-> " + line.getBytes(StandardCharsets.UTF_8).length);
//           });
            int sum = fileStream.mapToInt(line -> line.getBytes(StandardCharsets.UTF_8).length).sum();
            System.out.println(sum);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        for(Path file: files){
//            try(Stream<String> fileStream = Files.lines(file)) {
//                System.out.println((int)fileStream.count());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

    }

    private static boolean isSizeGood(Path file){
        try(Stream<String> fileStream = Files.lines(file)) {
            int sum = fileStream.mapToInt(line -> line.getBytes(StandardCharsets.UTF_8).length).sum();
            return sum < 1024;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
