package properties.model;

public record ConfigProperties(long sleepTime, int maxChunkSize, int maxRowsSize, String startDir) {
}
