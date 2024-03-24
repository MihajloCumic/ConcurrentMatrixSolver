package finder;

import system.explorer.finder.FileFinder;

public class FIleFinderTest {
    public static void main(String[] args) {
        FileFinder fileFinder = new FileFinder("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test", ".rix");
        fileFinder.findFiles();
    }
}
