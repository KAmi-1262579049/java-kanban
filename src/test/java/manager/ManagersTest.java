package manager;

import task.Task;
import task.TaskStatus;
import task.Epic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс ManagersTest
class ManagersTest {

    // Тест проверяет, что метод getDefault() возвращает готовый к работе TaskManager
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

    // Тест проверяет, что метод getDefaultHistory() возвращает готовый к работе HistoryManager
    @Test
    void testGetDefaultHistoryReturnsInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Метод getDefaultHistory() должен возвращать не-null объект");

        assertDoesNotThrow(() -> {
            historyManager.getHistory();
        }, "Методы менеджера истории не должны выбрасывать исключения");
    }

    // Тест проверяет базовую функциональность менеджера задач
    @Test
    void testTaskManagerCanManageTasks() {
        TaskManager taskManager = Managers.getDefault();

        Task taskToCreate = new Task("Test Task", "Description");
        Task createdTask = taskManager.createTask(taskToCreate);
        assertNotNull(createdTask.getId(), "Задача должна иметь id после создания");

        Task retrievedTask = taskManager.getTaskById(createdTask.getId());
        assertEquals(createdTask, retrievedTask, "Созданная и полученная задачи должны быть равны");

        assertNotNull(taskManager.getHistory(), "История не должна быть null");
    }

    // Тест проверяет создание обычной задачи и проверку её свойств
    @Test
    void testTaskManagerCanCreateDifferentTaskTypes() {
        TaskManager taskManager = Managers.getDefault();

        Task task = new Task("Обычная задача", "Описание обычной задачи");
        Task createdTask = taskManager.createTask(task);
        assertNotNull(createdTask.getId());
        assertEquals("Обычная задача", createdTask.getName());
        assertEquals(TaskStatus.NEW, createdTask.getStatus());

        assertEquals(1, taskManager.getAllTasks().size());
        assertTrue(taskManager.getAllTasks().contains(createdTask));
    }

    // Тест проверяет создание эпика и проверку его свойств
    @Test
    void testTaskManagerCanCreateEpic() {
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Эпик", "Описание эпика");
        Epic createdEpic = taskManager.createEpic(epic);

        assertNotNull(createdEpic.getId());
        assertEquals("Эпик", createdEpic.getName());
        assertEquals(TaskStatus.NEW, createdEpic.getStatus());
    }
}
