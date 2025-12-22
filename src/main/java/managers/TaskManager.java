package managers;

import task.Epic;
import task.Subtask;
import task.Task;
import java.util.ArrayList;

// Интерфейс менеджера задач
public interface TaskManager {
    // Метод для получения списка всех обычных задач
    ArrayList<Task> getTasks();

    // Метод для получения задачи по id
    Task getTask(Integer id);

    // Метод для добавления новой задачи
    Integer addNewTask(Task task);

    // Метод для обновления существующей задачи
    void updateTask(Task task);

    // Метод для удаления задачи по id
    void deleteTask(Integer id);

    // Метод для удаления всех обычных задач
    void deleteAllTasks();

    // Метод для получения списка всех эпиков
    ArrayList<Epic> getEpics();

    // Метод для получения эпика по id
    Epic getEpic(Integer id);

    // Метод для добавления нового эпика
    Integer addNewEpic(Epic epic);

    // Метод для обновления существующего эпика
    void updateEpic(Epic epic);

    // Метод для удаления эпика по id
    void deleteEpic(Integer id);

    // Метод для удаления всех эпиков
    void deleteAllEpics();

    // Метод для получения списка всех подзадач
    ArrayList<Subtask> getSubtasks();

    // Метод для получения подзадачи по id
    Subtask getSubtask(Integer id);

    // Метод для добавления новой подзадачи
    Integer addNewSubtask(Subtask subtask);

    // Метод для обновления существующей подзадачи
    void updateSubtask(Subtask subtask);

    // Метод для удаления подзадачи по id
    void deleteSubtask(Integer id);

    // Метод для удаления всех подзадач
    void deleteAllSubtasks();

    // Метод для получения всех подзадач определенного эпика
    ArrayList<Subtask> getEpicSubtasks(Integer epicId);

    // Метод для получения истории просмотров задач
    ArrayList<Task> getHistory();
}