package coordinator;

import task.Task;

public interface Coordinator {
    void delegateTask(Task task);
}
