package coordinator.thread;

import coordinator.Coordinator;
import coordinator.delegator.TaskCoordinator;
import queue.TaskQueue;
import task.Task;
import task.type.TaskType;

public class CoordinatorThread extends Coordinator {
    private final TaskCoordinator coordinator;
    private final TaskQueue taskQueue;

    public CoordinatorThread(TaskCoordinator coordinator, TaskQueue taskQueue){
        this.coordinator = coordinator;
        this.taskQueue = taskQueue;
    }
    @Override
    public void run() {
        while(true){
            try {
                Task task = taskQueue.takeTask();
                coordinator.delegateTask(task);
                if(task.getType().equals(TaskType.POISON_PILL)){
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Task coordinator shutdown.");
    }
}
