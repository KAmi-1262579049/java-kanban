package manager;

import task.Task;
import java.util.ArrayList;

// Интерфейс для управления историей просмотров задач
public interface HistoryManager {
    // Метод для добавления задачи в историю просмотров
    void add(Task task);
    // Метод для удаления задачи из истории по её идентификатору
    void remove(int id);
    // Возвращение списка всех задач из истории просмотров
    ArrayList<Task> getHistory();
}
