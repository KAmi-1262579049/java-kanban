package manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import task.Task;
import task.Epic;
import task.Subtask;
import task.TaskStatus;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для InMemoryTaskManager
class InMemoryTaskManagerTest {
    private TaskManager taskManager; // Переменная для хранения экземпляра менеджера задач

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    // Тест создания и получения задачи
    @Test
    void testCreateAndRetrieveTask() {
        Task task = new Task("Test Task", "Test Description");
        Task createdTask = taskManager.createTask(task);

        assertNotNull(createdTask.getId());
        assertEquals("Test Task", createdTask.getName());
        assertEquals(TaskStatus.NEW, createdTask.getStatus());

        Task retrievedTask = taskManager.getTaskById(createdTask.getId());
        assertEquals(createdTask, retrievedTask);
    }

    // Тест создания и получения эпика
    @Test
    void testCreateAndRetrieveEpic() {
        Epic epic = new Epic("Test Epic", "Test Description");
        Epic createdEpic = taskManager.createEpic(epic);

        assertNotNull(createdEpic.getId());
        assertEquals("Test Epic", createdEpic.getName());
        assertEquals(TaskStatus.NEW, createdEpic.getStatus());

        Epic retrievedEpic = taskManager.getEpicById(createdEpic.getId());
        assertEquals(createdEpic, retrievedEpic);
    }

    // Тест создания подзадачи с существующим эпиком
    @Test
    void testCreateSubtaskWithValidEpic() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Description"));
        Subtask subtask = new Subtask("Subtask", "Description", epic.getId());

        Subtask createdSubtask = taskManager.createSubtask(subtask);

        assertNotNull(createdSubtask.getId());
        assertEquals(epic.getId(), createdSubtask.getEpicId());

        List<Subtask> epicSubtasks = taskManager.getSubtasksByEpicId(epic.getId());
        assertEquals(1, epicSubtasks.size());
        assertEquals(createdSubtask.getId(), epicSubtasks.get(0).getId());
    }

    // Тест создания подзадачи с несуществующим эпиком
    @Test
    void testCreateSubtaskWithInvalidEpic() {
        Subtask subtask = new Subtask("Subtask", "Description", 999);

        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.createSubtask(subtask);
        }, "Должно быть исключение при создании подзадачи с несуществующим эпиком");
    }

    // Тест расчета статуса эпика на основе статусов подзадач
    @Test
    void testEpicStatusCalculation() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Description"));

        assertEquals(TaskStatus.NEW, epic.getStatus());

        Subtask subtask1 = taskManager.createSubtask(
                new Subtask("Subtask 1", "Description", epic.getId()));
        Subtask subtask2 = taskManager.createSubtask(
                new Subtask("Subtask 2", "Description", epic.getId()));

        assertEquals(TaskStatus.NEW, taskManager.getEpicById(epic.getId()).getStatus());

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);
        assertEquals(TaskStatus.IN_PROGRESS,
                taskManager.getEpicById(epic.getId()).getStatus());

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        assertEquals(TaskStatus.DONE, taskManager.getEpicById(epic.getId()).getStatus());
    }

    // Тест удаления задачи
    @Test
    void testDeleteTask() {
        Task task = taskManager.createTask(new Task("Task", "Description"));
        int taskId = task.getId();

        taskManager.deleteTaskById(taskId);

        assertNull(taskManager.getTaskById(taskId));
        assertEquals(0, taskManager.getAllTasks().size());
    }

    // Тест удаления эпика вместе с его подзадачами
    @Test
    void testDeleteEpicWithSubtasks() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Description"));
        Subtask subtask = taskManager.createSubtask(
                new Subtask("Subtask", "Description", epic.getId()));

        taskManager.deleteEpicById(epic.getId());

        assertNull(taskManager.getEpicById(epic.getId()));
        assertNull(taskManager.getSubtaskById(subtask.getId()));
        assertEquals(0, taskManager.getAllEpics().size());
        assertEquals(0, taskManager.getAllSubtasks().size());
    }

    // Тест получения всех задач
    @Test
    void testGetAllTasks() {
        Task task1 = taskManager.createTask(new Task("Task 1", "Description"));
        Task task2 = taskManager.createTask(new Task("Task 2", "Description"));
        Epic epic = taskManager.createEpic(new Epic("Epic", "Description"));

        List<Task> tasks = taskManager.getAllTasks();
        List<Epic> epics = taskManager.getAllEpics();

        assertEquals(2, tasks.size());
        assertEquals(1, epics.size());

        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertTrue(epics.contains(epic));
    }

    // Тест истории просмотров задач
    @Test
    void testHistoryOnTaskRetrieval() {
        Task task = taskManager.createTask(new Task("Task", "Description"));
        Epic epic = taskManager.createEpic(new Epic("Epic", "Description"));
        Subtask subtask = taskManager.createSubtask(
                new Subtask("Subtask", "Description", epic.getId()));

        // Получаем задачи для наполнения истории
        taskManager.getTaskById(task.getId());
        taskManager.getEpicById(epic.getId());
        taskManager.getSubtaskById(subtask.getId());

        List<Task> history = taskManager.getHistory();

        assertEquals(3, history.size());
        assertEquals(task.getId(), history.get(0).getId());
        assertEquals(epic.getId(), history.get(1).getId());
        assertEquals(subtask.getId(), history.get(2).getId());
    }

    // Тест уникальности id для разных типов задач
    @Test
    void testTaskIdsDoNotConflict() {
        Task task = taskManager.createTask(new Task("Task 1", "Description"));
        Epic epic = taskManager.createEpic(new Epic("Epic", "Description"));

        // ID не должны конфликтовать между разными типами задач
        assertNotEquals(task.getId(), epic.getId());

        // Получаем задачи по ID
        assertNotNull(taskManager.getTaskById(task.getId()));
        assertNotNull(taskManager.getEpicById(epic.getId()));
    }

    // Тест неизменяемости задачи после добавления в менеджер
    @Test
    void testTaskImmutabilityWhenAdded() {
        Task originalTask = new Task("Original", "Description");
        originalTask.setStatus(TaskStatus.IN_PROGRESS);

        Task createdTask = taskManager.createTask(originalTask);

        originalTask.setStatus(TaskStatus.DONE);
        originalTask.setName("Changed");

        Task retrievedTask = taskManager.getTaskById(createdTask.getId());

        assertEquals(TaskStatus.DONE, retrievedTask.getStatus());
        assertEquals("Changed", retrievedTask.getName());

        assertEquals(createdTask.getId(), retrievedTask.getId());
    }

    // Тест обновления задачи
    @Test
    void testUpdateTask() {
        Task task = taskManager.createTask(new Task("Original", "Description"));

        Task updatedTask = new Task(task.getId(), "Updated", "New Description",
                TaskStatus.IN_PROGRESS);
        taskManager.updateTask(updatedTask);

        Task retrievedTask = taskManager.getTaskById(task.getId());

        assertEquals("Updated", retrievedTask.getName());
        assertEquals("New Description", retrievedTask.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, retrievedTask.getStatus());
    }

    // Тест удаления всех задач
    @Test
    void testDeleteAllTasks() {
        taskManager.createTask(new Task("Task 1", "Description"));
        taskManager.createTask(new Task("Task 2", "Description"));

        assertEquals(2, taskManager.getAllTasks().size());

        taskManager.deleteAllTasks();

        assertEquals(0, taskManager.getAllTasks().size());
    }

    // Тест удаления всех эпиков
    @Test
    void testDeleteAllEpics() {
        Epic epic1 = taskManager.createEpic(new Epic("Epic 1", "Description"));
        Epic epic2 = taskManager.createEpic(new Epic("Epic 2", "Description"));

        taskManager.createSubtask(new Subtask("Subtask 1", "Description", epic1.getId()));
        taskManager.createSubtask(new Subtask("Subtask 2", "Description", epic2.getId()));

        assertEquals(2, taskManager.getAllEpics().size());
        assertEquals(2, taskManager.getAllSubtasks().size());

        taskManager.deleteAllEpics();

        assertEquals(0, taskManager.getAllEpics().size());
        assertEquals(0, taskManager.getAllSubtasks().size());
    }
}