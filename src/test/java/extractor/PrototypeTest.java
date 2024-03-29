package extractor;

import matrix.Matrix;
import matrix.impl.MatrixImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PrototypeTest {
    private static final int BYTES_LIMIT = 1024;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        ExecutorCompletionService<Matrix> results = new ExecutorCompletionService<>(pool);
        int numberOfJobs = divideTask(Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/a1c1_result.rix"), results);
        Matrix matrix = null;
        try {
            for(int i = 0; i < numberOfJobs; i++){
                matrix = results.take().get();
            }
            if(matrix != null){
                matrix.printMatrix(false);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        pool.shutdown();
    }

    private static int divideTask(Path file, ExecutorCompletionService<Matrix> results){
        int numberOfJobs = 0;
        Matrix matrix = new MatrixImpl(file);
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            boolean isFirst = true;
            int byteCount = 0;
            int startLine = 1;
            int endLine = 1;
            String line;

            while ((line = reader.readLine()) != null) {
                if(isFirst){
                    isFirst = false;
                    continue;
                }
                int tmp = byteCount + line.getBytes(StandardCharsets.UTF_8).length;

                if(tmp < BYTES_LIMIT){
                    byteCount = tmp;
                    endLine++;
                    continue;
                }
                if(tmp == BYTES_LIMIT){
                    //System.out.println("EQUALS:");
                    //System.out.println(tmp);
                    byteCount = 0;
                    //System.out.println("\tStart-> " + startLine);
                    //System.out.println("\tEnd-> " + endLine);
                    results.submit(new Worker(file, matrix, startLine, endLine));
                    numberOfJobs++;
                    startLine = endLine;
                    endLine++;
                    continue;

                }
                byteCount = line.getBytes(StandardCharsets.UTF_8).length;
//                System.out.println("BIGGER:");
//                System.out.println(tmp);
//                System.out.println("\tStart-> " + startLine);
//                System.out.println("\tEnd-> " + (endLine - 1));
                results.submit(new Worker(file, matrix, startLine, endLine - 1));
                numberOfJobs++;
                startLine = endLine - 1;
                endLine++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfJobs;
    }
}
