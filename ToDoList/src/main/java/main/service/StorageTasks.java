package main.service;

import main.model.Task;

import java.util.*;

public class StorageTasks {

    private static int keyId = 1;
    //создаем потокобезопасную коллекцию
    public static Hashtable<Integer, Task> mapTasks = new Hashtable<>();

    //возвращаем все задачи
    public static List<Task> getAllTasks() {
        ArrayList<Task> listTasks = new ArrayList<>();
        listTasks.addAll(mapTasks.values());
        return listTasks;
    }

    //возвращаем конкретную задачу по id
    public static Task getTask(int id) {
        return mapTasks.get(id);
    }

    //добавляем задачу
    public static int addTask(Task task) {
        int id = keyId;
        task.setId(id);
        mapTasks.put(keyId, task);
        keyId = id + 1;
        return keyId;
    }

    //изменение конкретной задачи
    public static void update(int id, Task task) {
        if (mapTasks.containsKey(id)) {
            task.setId(id);
            mapTasks.put(id, task);
        }
    }

    //удаление задачи
    public static void delete(int id) {
        mapTasks.remove(id);
    }


}
