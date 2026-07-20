package dev.tharvbytes.tasks.service;

import dev.tharvbytes.tasks.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final List<Task> tasks = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Task> getTasksByOwner(String email) {
        List<Task> ownerTasks = tasks.stream()
                .filter(task -> task.getOwnerEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
        
        // If the user is new and has no tasks, seed a couple of tasks for demonstration
        if (ownerTasks.isEmpty()) {
            addTask("Explore Tharvbytes Tasks dashboard", true, email);
            addTask("Verify identity claims (email, role, permissions) from I.Core", false, email);
            addTask("Test TASK_DELETE permission by attempting to delete a task", false, email);
            
            ownerTasks = tasks.stream()
                    .filter(task -> task.getOwnerEmail().equalsIgnoreCase(email))
                    .collect(Collectors.toList());
        }
        return ownerTasks;
    }

    public Task addTask(String title, boolean done, String ownerEmail) {
        Task task = new Task(idGenerator.getAndIncrement(), title, done, ownerEmail);
        tasks.add(task);
        return task;
    }

    public boolean completeTask(Long id, String ownerEmail) {
        for (Task task : tasks) {
            if (task.getId().equals(id) && task.getOwnerEmail().equalsIgnoreCase(ownerEmail)) {
                task.setDone(true);
                return true;
            }
        }
        return false;
    }

    public boolean deleteTask(Long id, String ownerEmail) {
        return tasks.removeIf(task -> task.getId().equals(id) && task.getOwnerEmail().equalsIgnoreCase(ownerEmail));
    }
}
