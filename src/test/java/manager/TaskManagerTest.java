package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Task;
import task.Epic;
import task.Subtask;
import task.TaskStatus;
import static org.junit.jupiter.api.Assertions.*;

// Класс для тестирования функциональности менеджера задач
class TaskManagerTest {
    private TaskManager taskManager; // Переменная для хранения экземпляра менеджера задач

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    // Тестовый метод для проверки создания задачи и её поиска по id
    @Test
    void testCreateAndFindTask() {
        Task task = taskManager.createTask(new Task("Task", "Description"));
        assertNotNull(taskManager.getTaskById(task.getId()));
    }

    // Тестовый метод для проверки расчёта статуса эпика на основе статусов подзадач
    @Test
    void testEpicStatusCalculation() {
        Epic epic = taskManager.createEpic(new Epic("Epic", "Description"));
        Subtask subtask = taskManager.createSubtask(
                new Subtask("Subtask", "Description", epic.getId())
        );

        subtask.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask);

        assertEquals(TaskStatus.DONE, taskManager.getEpicById(epic.getId()).getStatus());
    }

    // Тестовый метод для проверки работы менеджера истории просмотров
    @Test
    void testHistoryManager() {
        Task task = taskManager.createTask(new Task("Task", "Description"));

        taskManager.getTaskById(task.getId());

        assertEquals(1, taskManager.getHistory().size());
    }
}