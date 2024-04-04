package properties.impl;

import properties.Loader;
import properties.model.ConfigProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesLoader implements Loader {
    private final Path file;

    public PropertiesLoader(String fileName){
        this.file = Path.of(fileName);
    }
    @Override
    public ConfigProperties load() throws Exception {
        Properties properties = new Properties();
        properties.load(Files.newBufferedReader(file));
        long sleepTime = Long.parseLong(properties.getProperty("sys_explorer_sleep_time"));
        int maxChunkSize = Integer.parseInt(properties.getProperty("maximum_file_chunk_size"));
        int maxRowsSize = Integer.parseInt(properties.getProperty("maximum_rows_size"));
        String startDir = properties.getProperty("start_dir");

        return new ConfigProperties(sleepTime, maxChunkSize, maxRowsSize, startDir);
    }
}
