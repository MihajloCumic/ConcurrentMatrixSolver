package coordinator.impl;

import coordinator.Coordinator;
import extractor.pool.ExtractorPool;
import task.Task;
import task.type.TaskType;

public class TaskCoordinator implements Coordinator {
    private final ExtractorPool extractorPool;

    public TaskCoordinator(ExtractorPool extractorPool){
        this.extractorPool = extractorPool;
    }
    @Override
    public void delegateTask(Task task) {
        System.out.println("delegate: " + task);
        if(task.getType().equals(TaskType.CREATE) || task.getType().equals(TaskType.UPDATE)) extractorPool.submitTask(task);
    }
}
