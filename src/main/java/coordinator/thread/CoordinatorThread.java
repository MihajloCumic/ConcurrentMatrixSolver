package coordinator.thread;

import coordinator.Coordinator;
import queue.TaskQueue;
import task.Task;

public class CoordinatorThread implements Runnable{
    private final Coordinator coordinator;
    private final TaskQueue taskQueue;

    public CoordinatorThread(Coordinator coordinator, TaskQueue taskQueue){
        this.coordinator = coordinator;
        this.taskQueue = taskQueue;
    }
    @Override
    public void run() {
        while(true){
            try {
                Task task = taskQueue.takeTask();
                coordinator.delegateTask(task);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
