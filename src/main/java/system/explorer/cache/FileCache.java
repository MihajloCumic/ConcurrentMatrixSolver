package system.explorer.cache;

import logger.GlobalLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileCache {
    private final ConcurrentMap<Path, FileTime> lastModifiedCache;

    public FileCache(){
        this.lastModifiedCache = new ConcurrentHashMap<>();
    }

    public void put(Path file){
        try {
            FileTime fileTime = Files.getLastModifiedTime(file);
            lastModifiedCache.put(file, fileTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean wasModified(Path file){
        if(!lastModifiedCache.containsKey(file)) return false;
        try {
            FileTime newTime = Files.getLastModifiedTime(file);
            FileTime oldTime = lastModifiedCache.get(file);
            return newTime.compareTo(oldTime) > 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePathFromCache(Path path){
        if(containsKey(path)){
            lastModifiedCache.remove(path);
            GlobalLogger.getInstance().logInfo("SE removed file: " + path);
        }
    }

    public boolean containsKey(Path file){
        return lastModifiedCache.containsKey(file);
    }



}
