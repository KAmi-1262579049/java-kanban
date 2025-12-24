package manager;

import task.Task;
import task.Epic;
import task.Subtask;
import java.util.ArrayList;

// Интерфейс TaskManager
public interface TaskManager {
    ArrayList<Task> getAllTasks(); // Возвращает список всех обычных задач
    void deleteAllTasks(); // Удаляет все обычные задачи из менеджера
    Task getTaskById(int id); // Возвращает обычную задачу по её уникальному идентификатору
    Task createTask(Task task); // Создает новую обычную задачу и возвращает её
    void updateTask(Task task); // Обновляет существующую обычную задачу
    void deleteTaskById(int id); // Удаляет обычную задачу по её идентификатору

    ArrayList<Epic> getAllEpics(); // Возвращает список всех эпиков
    void deleteAllEpics(); // Удаляет все эпики и их подзадачи из менеджера
    Epic getEpicById(int id); // Возвращает эпик по его уникальному идентификатору
    Epic createEpic(Epic epic); // Создает новый эпик и возвращает его
    void updateEpic(Epic epic); // Обновляет существующий эпик
    void deleteEpicById(int id); // Удаляет эпик по его идентификатору вместе со всеми его подзадачами

    ArrayList<Subtask> getAllSubtasks(); // Возвращает список всех подзадач
    void deleteAllSubtasks(); // Удаляет все подзадачи
    Subtask getSubtaskById(int id); // Возвращает подзадачу по её уникальному идентификатору
    Subtask createSubtask(Subtask subtask); // Создает новую подзадачу
    void updateSubtask(Subtask subtask); // Обновляет существующую подзадачу
    void deleteSubtaskById(int id); // Удаляет подзадачу по её идентификатору

    ArrayList<Subtask> getSubtasksByEpicId(int epicId); // Возвращает список всех подзадач определенного эпика
    ArrayList<Task> getHistory(); // Возвращает историю просмотров задач
}
