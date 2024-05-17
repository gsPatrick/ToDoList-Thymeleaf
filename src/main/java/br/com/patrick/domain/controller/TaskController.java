package br.com.patrick.domain.controller;

import br.com.patrick.domain.model.Task;
import br.com.patrick.domain.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("newTask", new Task());
        return "tarefas";
    }

    @PostMapping("/add")
    public String addTask(@Valid @ModelAttribute("newTask") Task task) {
        taskService.save(task);
        return "redirect:/tasks/";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable("id") Long id, Model model) {
        taskService.completeTask(id);
        List<Task> tasks = taskService.findAll();
        tasks.removeIf(task -> task.getId().equals(id));
        model.addAttribute("tasks", tasks);

        return "redirect:/tasks/";
    }

    @GetMapping("/completed")
    public String listCompletedTasks(Model model) {
        model.addAttribute("completedTasks", taskService.getCompletedTasks());
        return "tarefasConcluidas";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/tasks/";
    }

    @GetMapping("/completed/delete/{id}")
    public String deleteCompletedTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "redirect:/tasks/completed";
    }


    @GetMapping("/deleteAll")
    public String deleteAllTasks() {
        taskService.deleteAll();
        return "redirect:/tasks/";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@Valid @PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("newTask", task);
        model.addAttribute("tasks", taskService.findAll());
        return "tarefas";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute("newTask") Task task) {
        taskService.save(task);
        return "redirect:/tasks/";
    }
}