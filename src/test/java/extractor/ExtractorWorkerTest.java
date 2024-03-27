package extractor;

import extractor.thread.ExtractorWorker;
import matrix.Matrix;
import matrix.impl.MatrixImpl;
import queue.TaskQueue;
import queue.impl.TaskQueueImpl;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class ExtractorWorkerTest {
    public static void main(String[] args) {
        Path file = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/a1c1_result.rix");
        String firstLine = "";
        try(Stream<String> lineStream = Files.lines(file)) {
            firstLine = lineStream.limit(1).toList().get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int linesNum;
        try(Stream<String> lineStream = Files.lines(file)) {
            linesNum = (int)lineStream.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //matrix_name=A2C2, rows=127, cols=117
        String[] parts = firstLine.split(",");
        String name = parts[0].trim().split("=")[1];
        int rowNum = Integer.parseInt(parts[1].trim().split("=")[1]);
        int colNum = Integer.parseInt(parts[2].trim().split("=")[1]);
        System.out.println(name);
        Matrix matrix = new MatrixImpl(file);
        System.out.println(linesNum);


        ForkJoinPool pool = new ForkJoinPool();

        try {
            Matrix matrix1 = pool.submit(new ExtractorWorker(1200000, file, matrix, 1, linesNum)).get();
            matrix1.printMatrix(true);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
