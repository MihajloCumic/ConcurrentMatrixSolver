package extractor.thread;

import matrix.Matrix;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class ExtractorWorker implements Callable<Matrix> {

    private final int start;
    private final int end;

    private final Path file;
    private final Matrix matrix;

    public ExtractorWorker(Path file, Matrix matrix, int start, int end){
        this.file = file;
        this.matrix = matrix;
        this.start = start;
        this.end = end;

    }
    @Override
    public Matrix call() throws Exception {
        try(Stream<String> lineStream = Files.lines(file)) {
            lineStream.skip(start).limit(end).forEach(line -> {
                if(line.isBlank()) return;
                putValuesInMatrix(line);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return matrix;
    }

    private void putValuesInMatrix(String line){
        String[] parts = line.split(",");

        int row = Integer.parseInt(parts[0].trim());

        String[] colValue = parts[1].split("=");
        int col = Integer.parseInt(colValue[0].trim());

        BigInteger value = new BigInteger(colValue[1].trim());

        matrix.putElement(row, col, value);
    }

}
