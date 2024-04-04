package coordinator;

import coordinator.delegator.TaskCoordinator;
import coordinator.thread.CoordinatorThread;
import extractor.Extractor;
import multiplier.Multiplier;
import queue.TaskQueue;

public abstract class Coordinator implements Runnable{
    public static Coordinator newTaskCoordinator(TaskQueue taskQueue, Extractor extractor, Multiplier multiplier){
        return new CoordinatorThread(new TaskCoordinator(extractor, multiplier), taskQueue);
    }
}
