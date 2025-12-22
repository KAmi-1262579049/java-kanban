package managers;

import task.Task;
import java.util.ArrayList;

// Интерфейс менеджера истории просмотров
public interface HistoryManager {
    // Метод для добавления задачи в историю просмотров
    void add(Task task);

    // Метод для получения истории просмотров
    ArrayList<Task> getHistory();

    // Метод для удаления задачи из истории по id
    void remove(Integer id);
}
