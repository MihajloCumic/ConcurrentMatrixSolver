package filetests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FIleSizeTest {
    public static void main(String[] args) {
        ///home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/a1c1_result.rix
        Path file = Path.of("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test/a1c1_result.rix");
        long numberOfLines = countLines(file);
        //System.out.println(numberOfLines);
        //System.out.println(getFileByteSize(file, 0, (int)numberOfLines));
        int start = 0;
        int end = (int)numberOfLines;
        while(getFileByteSize(file, start, end) > 1024){
            int mid = end % 2 == 0? end / 2 : (end / 2) + 1;
            end = mid;
            System.out.println(end);
            System.out.println("In Bytes: " + getFileByteSize(file, start, end));
        }

        try(Stream<String> lineStream = Files.lines(file)) {
            lineStream.skip(1).limit(2).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("+++++++++++++++++");
        try(Stream<String> lineStream = Files.lines(file)) {
            lineStream.skip(2).limit(3).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static int getFileByteSize(Path file, int start, int end){
        try(Stream<String> lineStream = Files.lines(file)) {
            int byteSize = lineStream.skip(start).limit(end).mapToInt(line -> line.getBytes(StandardCharsets.UTF_8).length).sum();
            return byteSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long countLines(Path file){
        try(Stream<String> lineStream = Files.lines(file)) {
            return lineStream.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
