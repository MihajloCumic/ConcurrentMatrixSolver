package cli;

import brain.MatrixBrain;
import matrix.Matrix;
import matrix.impl.MatrixImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class InfoAllTest {
    public static void main(String[] args) {
        Matrix m1 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica1.rix"));
        Matrix m2 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica2.rix"));
        Matrix m3 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica3.rix"));
        Matrix m4 = new MatrixImpl(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica4.rix"));
        MatrixBrain matrixBrain = new MatrixBrain(Executors.newCachedThreadPool());
        matrixBrain.cacheMatrix(m1);
        matrixBrain.cacheMatrix(m2);
        matrixBrain.cacheMatrix(m3);
        matrixBrain.cacheMatrix(m4);
        CommandLIneRunner cli = new CommandLIneRunner(matrixBrain);
        try {
            cli.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
