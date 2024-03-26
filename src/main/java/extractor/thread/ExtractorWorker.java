package extractor.thread;

import matrix.Matrix;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class ExtractorWorker extends RecursiveTask<Matrix> {
    private final int LIMIT;
    private final Matrix matrix;
    private final Path file;
    private int start;
    private int end;

    public ExtractorWorker(int LIMIT, Path file, Matrix matrix, int start, int end){
        this.LIMIT = LIMIT;
        this.matrix = matrix;
        this.file = file;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Matrix compute() {
        int size = getLinesByteSize();
        System.out.println("Outside IF: " + size);
        if(size <= LIMIT){
            System.out.println("IN If: " + size);
            System.out.println("Start:" + start + " End: " + end);
            try(Stream<String> lineStream = Files.lines(file)) {
                lineStream.skip(start).limit(end).forEach(line -> {
                    if(line.isBlank()) return;
                    String[] parts = line.split(",");
                    int row = Integer.parseInt(parts[0].trim());
                    String[] colValue = parts[1].split("=");
                    int col = Integer.parseInt(colValue[0].trim());
                    BigInteger value = new BigInteger(colValue[1].trim());
                    matrix.putElement(row, col, value);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else {

            ExtractorWorker left = new ExtractorWorker(LIMIT, file, matrix, start, start + 50);
            ExtractorWorker right = new ExtractorWorker(LIMIT, file, matrix, start + 51, end);

            left.fork();
            right.fork();
            left.join();
            right.join();
        }
        return matrix;
    }

    private int getLinesByteSize(){
        try(Stream<String> lineStream = Files.lines(file)) {
            int byteSize = lineStream.skip(start).limit(end).mapToInt(line -> line.getBytes(StandardCharsets.UTF_8).length).sum();
            return byteSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
