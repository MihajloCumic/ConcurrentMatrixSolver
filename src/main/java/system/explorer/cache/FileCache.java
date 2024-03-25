package system.explorer.cache;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileCache {
    private final ConcurrentMap<Path, FileTime> lastModifiedCache;

    public FileCache(){
        this.lastModifiedCache = new ConcurrentHashMap<>();
    }



}
