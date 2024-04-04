package coordinator.delegator;

import extractor.Extractor;
import extractor.pool.ExtractorPool;
import multiplier.Multiplier;
import multiplier.pool.MultiplierPool;
import task.Task;
import task.impl.CreateMatrixTask;
import task.impl.MultiplyMatrixTask;
import task.impl.PoisonPill;
import task.impl.UpdateMatrixTask;
import task.type.TaskType;

public class TaskCoordinator{
    private final Extractor extractor;
    private final Multiplier multiplier;

    public TaskCoordinator(Extractor extractor, Multiplier multiplier){
        this.extractor = extractor;
        this.multiplier = multiplier;
    }
    public void delegateTask(Task task) {
        System.out.println("Delegate: " + task);
        if(task instanceof CreateMatrixTask createMatrixTask) extractor.submitTask(createMatrixTask);
        if(task instanceof UpdateMatrixTask updateMatrixTask) extractor.submitTask(updateMatrixTask);
        if(task instanceof MultiplyMatrixTask multiplyMatrixTask) multiplier.submitTask(multiplyMatrixTask);
        if(task instanceof PoisonPill poisonPill){
            extractor.submitTask(poisonPill);
            multiplier.submitTask(poisonPill);
        }
    }
}
