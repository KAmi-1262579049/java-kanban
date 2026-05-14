package manager;

import task.Task;
import java.util.ArrayList;
import java.util.List;

// Класс реализует интерфейс HistoryManager для хранения истории просмотров задач
public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history; // Хранение истории просмотров
    private static final int MAX_HISTORY_SIZE = 10; // Максимальный размер истории согласно ТЗ

    // Конструктор класса
    public InMemoryHistoryManager() {
        this.history = new ArrayList<>();
    }

    // Метод добавления задачи в историю
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        history.add(task);

        if (history.size() > MAX_HISTORY_SIZE) {
            history.remove(0);
        }
    }

    // Метод удаления задачи из истории (добавлен, так как есть в интерфейсе)
    @Override
    public void remove(int id) {
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).getId() == id) {
                history.remove(i);
                i--;
            }
        }
    }

    // Метод получения истории просмотров
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
