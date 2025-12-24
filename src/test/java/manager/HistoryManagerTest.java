package manager;

import org.junit.jupiter.api.Test;
import task.Task;
import task.TaskStatus;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для проверки HistoryManager
class HistoryManagerTest {

    // Тест на добавление задачи в историю
    @Test
    void testAddToHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task(1, "Task 1", "Description", TaskStatus.NEW);

        historyManager.add(task);
        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(1, history.size());
        assertEquals(task, history.get(0));
    }

    // Тест на добавление null-задачи
    @Test
    void testAddNullTask() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        // Добавление null не должно приводить к исключению
        historyManager.add(null);
        assertEquals(0, historyManager.getHistory().size());
    }

    // Тест на удаление задачи из истории
    @Test
    void testRemoveFromHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        historyManager.remove(1);
        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(1, history.size());
        assertEquals(task2, history.get(0));
    }

    // Тест на удаление несуществующей задач
    @Test
    void testRemoveNonExistentTask() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task(1, "Task", "Description", TaskStatus.NEW);

        historyManager.add(task);
        historyManager.remove(999); // Удаление несуществующей задачи

        assertEquals(1, historyManager.getHistory().size());
    }

    // Тест на сохранение порядка задач в истории
    @Test
    void testHistoryOrder() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(task3, history.get(2));
    }

    // Тест на поведение при дублировании
    @Test
    void testDuplicateTaskMovesToEnd() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.add(task1); // Повторное добавление task1

        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(3, history.size()); // Дубликат удалён
        assertEquals(task2, history.get(0));
        assertEquals(task3, history.get(1));
        assertEquals(task1, history.get(2)); // task1 переместился в конец
    }
}
