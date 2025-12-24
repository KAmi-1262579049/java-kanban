package manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import task.Task;
import task.Epic;
import task.Subtask;
import task.TaskStatus;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

// Класс тестов для InMemoryHistoryManager
class InMemoryHistoryManagerTest {
    private HistoryManager historyManager; // Поле для хранения экземпляра HistoryManager

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    // Тест (проверка пустой истории при создании менеджера)
    @Test
    void testEmptyHistory() {
        ArrayList<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой при создании");
    }

    // Тест (проверка добавления разных типов задач в историю)
    @Test
    void testAddDifferentTaskTypes() {
        Task task = new Task(1, "Task", "Description", TaskStatus.NEW);
        Epic epic = new Epic(2, "Epic", "Description");
        Subtask subtask = new Subtask(3, "Subtask", "Description",
                TaskStatus.NEW, 2);

        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);

        ArrayList<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task, history.get(0));
        assertEquals(epic, history.get(1));
        assertEquals(subtask, history.get(2));
    }

    // Тест (удаление задачи из начала истории)
    @Test
    void testRemoveFromBeginning() {
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(1);

        ArrayList<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task2, history.get(0));
        assertEquals(task3, history.get(1));
    }

    // Тест (удаление задачи из середины истории)
    @Test
    void testRemoveFromMiddle() {
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(2);
        ArrayList<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task3, history.get(1));
    }

    // Тест (удаление задачи из конца истории)
    @Test
    void testRemoveFromEnd() {
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        historyManager.remove(3);

        ArrayList<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
    }

    // Тест (проверка отсутствия ограничения на количество задач в истории)
    @Test
    void testHistoryLimitNotExceeded() {
        for (int i = 1; i <= 15; i++) {
            Task task = new Task(i, "Task " + i, "Description", TaskStatus.NEW);
            historyManager.add(task);
        }

        ArrayList<Task> history = historyManager.getHistory();
        assertEquals(15, history.size(), "История должна хранить все добавленные задачи");
    }

    // Тест (проверка, что история хранит ссылки на объекты, а не копии)
    @Test
    void testTaskUpdatesInHistory() {
        Task task = new Task(1, "Original", "Description", TaskStatus.NEW);
        historyManager.add(task);

        task.setName("Updated");
        task.setStatus(TaskStatus.DONE);

        ArrayList<Task> history = historyManager.getHistory();
        Task historyTask = history.get(0);

        assertEquals("Updated", historyTask.getName());
        assertEquals(TaskStatus.DONE, historyTask.getStatus());
    }
}