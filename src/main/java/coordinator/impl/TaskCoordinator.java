package coordinator.impl;

import coordinator.Coordinator;
import extractor.pool.ExtractorPool;
import multiplier.pool.MultiplierPool;
import task.Task;
import task.type.TaskType;

public class TaskCoordinator implements Coordinator {
    private final ExtractorPool extractorPool;
    private final MultiplierPool multiplierPool;

    public TaskCoordinator(ExtractorPool extractorPool, MultiplierPool multiplierPool){
        this.extractorPool = extractorPool;
        this.multiplierPool = multiplierPool;
    }
    @Override
    public void delegateTask(Task task) {
        System.out.println("Delegate: " + task);
        if(task.getType().equals(TaskType.CREATE)) extractorPool.submitTask(task);
        if(task.getType().equals(TaskType.MULTIPLY)) multiplierPool.submitTask(task);
    }
}
