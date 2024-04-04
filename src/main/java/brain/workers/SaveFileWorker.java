package brain.workers;

import matrix.Matrix;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

public class SaveFileWorker implements Callable<String> {
    private final String matrixName;
    private final Path file;
    private final ConcurrentMap<String, Matrix> matrices;

    public SaveFileWorker(String matrixName, Path file, ConcurrentMap<String, Matrix> matrices) {
        this.matrixName = matrixName;
        this.file = file;
        this.matrices = matrices;
    }

    @Override
    public String call() throws Exception {
        if(!matrices.containsKey(matrixName)) return "No matrix of name: " + matrixName;
        Matrix matrix = matrices.get(matrixName);
        if(!matrix.getFilePath().equals("No file.")) return "Matrix is already saved in: " + matrix.getFilePath();
        String content = matrix.getMatrixContentAsString();
        Path newFile = Path.of(file + "/" + matrixName + ".rix");
        Files.write(newFile, content.getBytes(), StandardOpenOption.CREATE);
        matrix.setFile(file);
        return "Matrix has been successfully saved in file: " + file;
    }
}
