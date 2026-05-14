package manager;

import task.Task;
import task.Epic;
import task.Subtask;
import task.TaskStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// Класс тестов для InMemoryHistoryManager
class InMemoryHistoryManagerTest {
    private HistoryManager historyManager; // Поле для хранения экземпляра менеджера истории

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    // Тест проверяет, что при создании менеджера история пустая
    @Test
    void testEmptyHistory() {
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой при создании");
    }

    // Тест проверяет, что при создании менеджера история пустая
    @Test
    void testAddDifferentTaskTypes() {
        Task task = new Task(1, "Task", "Description", TaskStatus.NEW);
        Epic epic = new Epic(2, "Epic", "Description");
        Subtask subtask = new Subtask(3, "Subtask", "Description",
                TaskStatus.NEW, 2);

        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task, history.get(0));
        assertEquals(epic, history.get(1));
        assertEquals(subtask, history.get(2));
    }

    // Тест проверяет ограничение размера истории
    @Test
    void testHistorySizeLimit() { // Изменено имя, чтобы избежать конфликта
        for (int i = 1; i <= 15; i++) {
            Task task = new Task(i, "Task " + i, "Description", TaskStatus.NEW);
            historyManager.add(task);
        }

        List<Task> history = historyManager.getHistory();
        assertEquals(10, history.size());
        assertEquals(6, history.get(0).getId());
        assertEquals(15, history.get(9).getId());
    }

    // Тест проверяет сохранение порядка добавления задач в историю
    @Test
    void testOrderPreserved() {
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(task3, history.get(2));
    }
}
