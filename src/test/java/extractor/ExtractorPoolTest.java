package extractor;

import extractor.pool.ExtractorPool;
import matrix.Matrix;
import task.Task;
import task.impl.CreateMatrixTask;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExtractorPoolTest {
    private static final Path file1 = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica1.rix");
    private static final Path file2 = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica2.rix");
    private static final Path file3 = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica3.rix");
    private static final Path file4 = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/moja-matrica4.rix");

    private static final Path bigFile1 = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/folder-2/a3c3_result.rix");
    private static final Path bigFile2 = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/folder-2/c4.rix");


    public static void main(String[] args) {
        CreateMatrixTask task1 = new CreateMatrixTask(bigFile1);
        CreateMatrixTask task2 = new CreateMatrixTask(bigFile2);


        List<CreateMatrixTask> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        ExtractorPool extractorPool = new ExtractorPool();
        List<Future<Matrix>> futures = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            for(CreateMatrixTask task: tasks){
                Future<Matrix> future = extractorPool.submitTask(task);
                futures.add(future);
            }
        }
        for(Future<Matrix> future: futures){
            try {
                System.out.println(future.get().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static void smallFilesTEst(){
        CreateMatrixTask task1 = new CreateMatrixTask(file1);
        CreateMatrixTask task2 = new CreateMatrixTask(file2);
        CreateMatrixTask task3 = new CreateMatrixTask(file3);
        CreateMatrixTask task4 = new CreateMatrixTask(file4);

        List<CreateMatrixTask> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);

        ExtractorPool extractorPool = new ExtractorPool();
        List<Future<Matrix>> futures = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            for(CreateMatrixTask task: tasks){
                Future<Matrix> future = extractorPool.submitTask(task);
                futures.add(future);
            }
        }
        for(Future<Matrix> future: futures){
            try {
                future.get().printMatrix(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
