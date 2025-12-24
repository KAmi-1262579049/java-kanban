package manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import task.Task;

// Тестовый класс ManagersTest
class ManagersTest {

    // Тест метода getDefault()
    @Test
    void testGetDefaultReturnsInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Метод getDefault() должен возвращать не-null объект");

        assertDoesNotThrow(() -> {
            taskManager.getAllTasks();
            taskManager.getAllEpics();
            taskManager.getAllSubtasks();
        }, "Методы менеджера не должны выбрасывать исключения");
    }

    // Тест getDefaultHistory()
    @Test
    void testGetDefaultHistoryReturnsInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Метод getDefaultHistory() должен возвращать не-null объект");

        assertDoesNotThrow(() -> {
            historyManager.getHistory();
        }, "Методы менеджера истории не должны выбрасывать исключения");
    }

    // Тест базовой функциональности менеджера
    @Test
    void testTaskManagerCanManageTasks() {
        TaskManager taskManager = Managers.getDefault();

        Task task = taskManager.createTask(new Task("Test Task", "Description"));
        assertNotNull(task.getId());

        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertEquals(task, retrievedTask);

        taskManager.getHistory();
    }
}