package coordinator.impl;

import coordinator.Coordinator;
import extractor.pool.ExtractorPool;
import multiplier.pool.MultiplierPool;
import task.Task;
import task.impl.CreateMatrixTask;
import task.impl.PoisonPill;
import task.impl.UpdateMatrixTask;
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
        if(task instanceof CreateMatrixTask createMatrixTask) extractorPool.submitTask(createMatrixTask);
        if(task instanceof UpdateMatrixTask updateMatrixTask) extractorPool.submitTask(updateMatrixTask);
        if(task.getType().equals(TaskType.MULTIPLY)) multiplierPool.submitTask(task);
        if(task instanceof PoisonPill poisonPill){
            extractorPool.submitTask(poisonPill);
            multiplierPool.submitTask(task);
        }
    }
}
