package managers.impl;

import managers.HistoryManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

// Класс InMemoryHistoryManager отвечает за хранение истории просмотров задач в оперативной памяти
public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10; // Константа, определяющая максимальный размер истории просмотров
    private final ArrayList<Task> history = new ArrayList<>(); // Список для хранения истории просмотров задач

    // Метод для добавления задачи в историю просмотров
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        Task taskCopy = createCopy(task);
        if (history.size() >= MAX_HISTORY_SIZE) {
            history.remove(0);
        }
        history.add(taskCopy);
    }

    // Метод для получения истории просмотров
    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }

    // Метод для удаления задачи из истории по ее идентификатору
    @Override
    public void remove(Integer id) {
        for (int i = history.size() - 1; i >= 0; i--) {
            Task task = history.get(i);

            if (task.getId().equals(id)) {
                history.remove(i);
            }
        }
    }

    // Вспомогательный метод для создания копии задачи
    private Task createCopy(Task task) {
        if (task instanceof Epic) {
            return new Epic((Epic) task);
        } else if (task instanceof Subtask) {
            return new Subtask((Subtask) task);
        } else {
            return new Task(task);
        }
    }
}