package coordinator.impl;

import coordinator.Coordinator;
import task.Task;

public class TaskCoordinator implements Coordinator {
    //MatrixExtractor
    //MatrixMultiplier
    @Override
    public void delegateTask(Task task) {
        System.out.println("delegate: " + task);
    }
}
