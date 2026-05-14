package manager;

import task.Task;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для проверки функциональности TaskManager
class TaskManagerTest {
    private TaskManager taskManager; // Поле для хранения экземпляра менеджера задач

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    // Тест проверяет создание задачи и её поиск по идентификатору
    @Test
    void testCreateAndFindTask() {
        Task task = taskManager.createTask(new Task("Task", "Description"));
        Task foundTask = taskManager.getTaskById(task.getId());

        assertNotNull(foundTask);
        assertEquals(task.getId(), foundTask.getId());
        assertEquals("Task", foundTask.getName());
    }

    // Тест проверяет работу менеджера истории просмотров
    @Test
    void testHistoryManager() {
        Task task = taskManager.createTask(new Task("Task", "Description"));
        taskManager.getTaskById(task.getId());

        List<Task> history = taskManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task.getId(), history.get(0).getId());
    }

    // Тест проверяет поведение истории при дублировании просмотров одной задачи
    @Test
    void testHistoryWithDuplicates() {
        Task task = taskManager.createTask(new Task("Task", "Description"));

        taskManager.getTaskById(task.getId());
        taskManager.getTaskById(task.getId());
        taskManager.getTaskById(task.getId());

        List<Task> history = taskManager.getHistory();
        assertEquals(3, history.size(), "Дубликаты должны сохраняться в истории");
    }
}
