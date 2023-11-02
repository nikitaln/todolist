package main.controllers;

import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoListController {

    //импорт репозитория чтобы пользоваться в контроллере
    //автоматически создавался для доступа
    @Autowired
    private TaskRepository taskRepository;

    //получение списка всех дел
    @GetMapping("/tasks/")
    public List<Task> list() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable) {
            tasks.add(task);
        }
        return tasks;
    }

    //получить конкретное дело
    @GetMapping(value = "/tasks/{id}")
    public ResponseEntity getTask(@PathVariable int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return  new ResponseEntity(taskOptional.get(), HttpStatus.OK);
    }

    //добавить дело
    @RequestMapping(value = "/tasks/", method = RequestMethod.POST)
    public int add(Task task) {

        //добавляем время создания задачи
        task.setDateTime(LocalDate.now());
        //сохраняем книгу и возвращает Сущность
        Task newTask = taskRepository.save(task);
        return newTask.getId();
    }

    //обновление дела
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT)
    public void updateTask(@PathVariable int id, Task task) {

        taskRepository.save(task);
    }

    //удаление дела по id
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        System.out.println("Контроллер - удаление книги " + id);
        taskRepository.deleteById(id);
    }
}
