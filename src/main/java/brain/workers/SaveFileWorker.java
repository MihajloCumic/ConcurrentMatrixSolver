package brain.workers;

import matrix.Matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class SaveFileWorker implements Runnable {
    private final String matrixName;
    private final Path file;
    private final ConcurrentMap<String, Matrix> matrices;

    public SaveFileWorker(String matrixName, Path file, ConcurrentMap<String, Matrix> matrices) {
        this.matrixName = matrixName;
        this.file = file;
        this.matrices = matrices;
    }

    @Override
    public void run() {
        if(!matrices.containsKey(matrixName)) {
            System.out.println("No matrix of name: " + matrixName);
            return;
        }

        Matrix matrix = matrices.get(matrixName);
        if(!matrix.getFilePath().equals("No file.")){
            System.out.println("Matrix is already saved in: " + matrix.getFilePath());
        }
        String content = matrix.getMatrixContentAsString();
        Path newFile = Path.of(file + "/" + matrixName + ".rix");
        try {
            Files.write(newFile, content.getBytes(), StandardOpenOption.CREATE);
            matrix.setFile(file);
            System.out.println("Matrix has been successfully saved in file: " + file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
