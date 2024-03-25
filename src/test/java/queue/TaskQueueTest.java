package queue;

import queue.impl.TaskQueueImpl;
import system.explorer.creator.TaskCreator;
import system.explorer.finder.FileFinder;
import task.Task;

public class TaskQueueTest {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueueImpl();
        TaskCreator taskCreator = new TaskCreator(taskQueue);
        FileFinder fileFinder = new FileFinder("/home/cuma/Fakultet/letnji-semestar/konkurenti-distribuirani/kids-test", ".rix", taskCreator);
        fileFinder.findFiles();

        while(!taskQueue.isEmpty()){
            Task task = taskQueue.takeTask();
            System.out.println(task.getType());

        }
    }
}
