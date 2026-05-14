package manager;

import task.Task;
import task.TaskStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для проверки HistoryManager
class HistoryManagerTest {
    private HistoryManager historyManager; // Переменная для хранения экземпляра HistoryManager
    private Task testTask; // Переменная для тестовой задачи

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
        testTask = new Task(1, "Task 1", "Description", TaskStatus.NEW);
    }

    // Тест проверяет добавление задачи в историю
    @Test
    void testAddToHistory() {
        historyManager.add(testTask);
        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size());
        assertEquals(testTask, history.get(0));
    }

    // Тест проверяет добавление null-задачи в историю
    @Test
    void testAddNullTask() {
        historyManager.add(null);
        assertEquals(0, historyManager.getHistory().size());
    }

    // Тест проверяет ограничение истории 10 элементами
    @Test
    void testHistoryLimit() {
        for (int i = 1; i <= 12; i++) {
            Task task = new Task(i, "Task " + i, "Description", TaskStatus.NEW);
            historyManager.add(task);
        }

        List<Task> history = historyManager.getHistory();

        // После добавления 12 задач, первые 2 (id=1 и id=2) должны быть удалены
        assertEquals(10, history.size(), "История должна содержать 10 элементов");
        assertEquals(3, history.get(0).getId(), "Первая задача должна иметь id=3");
        assertEquals(12, history.get(9).getId(), "Последняя задача должна иметь id=12");
    }

    // Тест проверяет разрешение дубликатов в истории
    @Test
    void testDuplicatesAllowed() {
        historyManager.add(testTask);
        historyManager.add(testTask);
        historyManager.add(testTask);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
    }

    // Тест проверяет метод remove()
    @Test
    void testRemoveFromHistory() {
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);

        historyManager.remove(1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task2, history.get(0));
    }
}
