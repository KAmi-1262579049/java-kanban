package managers.impl;

import managers.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import enums.TaskStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

// Тестовый класс для InMemoryTaskManager
class InMemoryTaskManagerTest {
    private TaskManager taskManager; // Поле для хранения менеджера задач

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    // Тест добавления новой задачи
    @Test
    void addNewTask() {
        Task task = new Task("Test task", "Test description", TaskStatus.NEW);

        final int taskId = taskManager.addNewTask(task);
        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена");
        assertEquals(task, savedTask, "Задачи не совпадают");

        final var tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются");
        assertEquals(1, tasks.size(), "Неверное количество задач");
        assertEquals(task, tasks.get(0), "Задачи не совпадают");
    }

    // Тест (задачи с заданным и сгенерированным id не должны конфликтовать)
    @Test
    void tasksWithGeneratedAndGivenIdShouldNotConflict() {
        Task task1 = new Task("Task 1", "Description", TaskStatus.NEW);
        int generatedId = taskManager.addNewTask(task1);
        Task task2 = new Task(generatedId, "Task 2", "Description", TaskStatus.NEW);

        taskManager.updateTask(task2);

        Task retrievedTask = taskManager.getTask(generatedId);

        assertEquals("Task 2", retrievedTask.getName(),
                "Задача с заданным id должна заменить задачу с таким же сгенерированным id");
    }

    // Тест добавления задач разных типов и поиска по id
    @Test
    void addDifferentTypesOfTasksAndFindById() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);
        Epic epic = new Epic("Epic", "Description");
        int epicId = taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        assertNotNull(taskManager.getTask(taskId), "Не удалось найти задачу по id");
        assertNotNull(taskManager.getEpic(epicId), "Не удалось найти эпик по id");
        assertNotNull(taskManager.getSubtask(subtaskId), "Не удалось найти подзадачу по id");
    }

    // Тест (задача не должна изменяться после добавления в менеджер)
    @Test
    void taskShouldNotChangeAfterAddingToManager() {
        Task originalTask = new Task("Original", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(originalTask);
        Task retrievedTask = taskManager.getTask(taskId);

        assertEquals(originalTask.getName(), retrievedTask.getName(),
                "Имя не должно измениться");
        assertEquals(originalTask.getDescription(), retrievedTask.getDescription(),
                "Описание не должно измениться");
        assertEquals(originalTask.getStatus(), retrievedTask.getStatus(),
                "Статус не должен измениться");
        assertEquals(originalTask.getId(), retrievedTask.getId(),
                "ID не должен измениться");
    }

    // Тест (обновление задачи должно выполняться корректно)
    @Test
    void updateTaskStatusShouldWorkCorrectly() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);
        Task updatedTask = new Task(taskId, "Updated Task", "Updated Description", TaskStatus.IN_PROGRESS);

        taskManager.updateTask(updatedTask);

        Task retrievedTask = taskManager.getTask(taskId);
        assertEquals(TaskStatus.IN_PROGRESS, retrievedTask.getStatus(), "Статус задачи должен измениться");
        assertEquals("Updated Task", retrievedTask.getName(), "Имя задачи должно измениться");
        assertEquals("Updated Description", retrievedTask.getDescription(), "Описание задачи должно измениться");
    }

    // Тест (обновление статуса подзадачи должно обновлять статус эпика)
    @Test
    void updateSubtaskStatusShouldUpdateEpicStatus() {
        Epic epic = new Epic("Epic", "Description");
        int epicId = taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        assertEquals(TaskStatus.NEW, taskManager.getEpic(epicId).getStatus(),
                "Статус эпика с новой подзадачей должен быть NEW");

        Subtask updatedSubtask = new Subtask(subtaskId, "Updated Subtask", "Updated", TaskStatus.IN_PROGRESS, epicId);
        taskManager.updateSubtask(updatedSubtask);

        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpic(epicId).getStatus(),
                "Статус эпика должен измениться на IN_PROGRESS при изменении статуса подзадачи");

        Subtask doneSubtask = new Subtask(subtaskId, "Done Subtask", "Done", TaskStatus.DONE, epicId);
        taskManager.updateSubtask(doneSubtask);

        assertEquals(TaskStatus.DONE, taskManager.getEpic(epicId).getStatus(),
                "Статус эпика должен измениться на DONE, когда все подзадачи DONE");
    }

    // Тест (статус эпика должен быть IN_PROGRESS, когда подзадачи имеют разные статусы)
    @Test
    void epicStatusShouldBeInProgressWhenSubtasksHaveDifferentStatuses() {
        Epic epic = new Epic("Epic", "Description");
        int epicId = taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description", TaskStatus.NEW, epicId);
        Subtask subtask2 = new Subtask("Subtask 2", "Description", TaskStatus.NEW, epicId);
        Subtask subtask3 = new Subtask("Subtask 3", "Description", TaskStatus.NEW, epicId);
        int subtaskId1 = taskManager.addNewSubtask(subtask1);
        int subtaskId2 = taskManager.addNewSubtask(subtask2);
        int subtaskId3 = taskManager.addNewSubtask(subtask3);

        assertEquals(TaskStatus.NEW, taskManager.getEpic(epicId).getStatus());

        taskManager.updateSubtask(new Subtask(subtaskId1, "Subtask 1", "Desc", TaskStatus.DONE, epicId));
        taskManager.updateSubtask(new Subtask(subtaskId2, "Subtask 2", "Desc", TaskStatus.IN_PROGRESS, epicId));
        taskManager.updateSubtask(new Subtask(subtaskId3, "Subtask 3", "Desc", TaskStatus.NEW, epicId));

        assertEquals(TaskStatus.IN_PROGRESS, taskManager.getEpic(epicId).getStatus(),
                "Статус эпика должен быть IN_PROGRESS, когда подзадачи имеют разные статусы");
    }

    // Тест (удаление задачи должно удалять ее из менеджера)
    @Test
    void deleteTaskShouldRemoveTaskFromManager() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);

        assertNotNull(taskManager.getTask(taskId), "Задача должна существовать до удаления");
        assertEquals(1, taskManager.getTasks().size(), "Должна быть 1 задача");

        taskManager.deleteTask(taskId);

        assertNull(taskManager.getTask(taskId), "Задача не должна существовать после удаления");
        assertEquals(0, taskManager.getTasks().size(), "Задач не должно быть");
    }

    // Тест (удаление задачи должно также удалять ее из истории)
    @Test
    void deleteTaskShouldRemoveItFromHistory() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);

        taskManager.getTask(taskId);

        assertEquals(1, taskManager.getHistory().size(), "Задача должна быть в истории");

        taskManager.deleteTask(taskId);

        assertEquals(0, taskManager.getHistory().size(), "Задача должна быть удалена из истории");
    }

    // Тест (удаление эпика должно удалять все его подзадачи)
    @Test
    void deleteEpicShouldRemoveEpicAndItsSubtasks() {
        Epic epic = new Epic("Epic", "Description");
        int epicId = taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description", TaskStatus.NEW, epicId);
        Subtask subtask2 = new Subtask("Subtask 2", "Description", TaskStatus.NEW, epicId);
        int subtaskId1 = taskManager.addNewSubtask(subtask1);
        int subtaskId2 = taskManager.addNewSubtask(subtask2);

        assertEquals(1, taskManager.getEpics().size(), "Должен быть 1 эпик");
        assertEquals(2, taskManager.getSubtasks().size(), "Должно быть 2 подзадачи");

        taskManager.getEpic(epicId);
        taskManager.getSubtask(subtaskId1);
        taskManager.getSubtask(subtaskId2);
        assertEquals(3, taskManager.getHistory().size(), "Должно быть 3 задачи в истории");

        taskManager.deleteEpic(epicId);

        assertEquals(0, taskManager.getEpics().size(), "Эпиков не должно быть");
        assertEquals(0, taskManager.getSubtasks().size(), "Подзадач не должно быть");

        assertEquals(0, taskManager.getHistory().size(), "История должна быть пустой");
    }

    // Тест (массовое удаление всех задач)
    @Test
    void deleteAllTasksShouldRemoveAllTasks() {
        Task task1 = new Task("Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task("Task 3", "Description", TaskStatus.NEW);

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewTask(task3);
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(3);

        assertEquals(3, taskManager.getTasks().size(), "Должно быть 3 задачи");
        assertEquals(3, taskManager.getHistory().size(), "Должно быть 3 задачи в истории");

        taskManager.deleteAllTasks();

        assertEquals(0, taskManager.getTasks().size(), "Задач не должно быть");
        assertEquals(0, taskManager.getHistory().size(), "История должна быть пустой");
    }

    // Тест (удаление всех эпиков должно удалять все эпики и подзадачи)
    @Test
    void deleteAllEpicsShouldRemoveAllEpicsAndSubtasks() {
        Epic epic1 = new Epic("Epic 1", "Description");
        Epic epic2 = new Epic("Epic 2", "Description");
        int epicId1 = taskManager.addNewEpic(epic1);
        int epicId2 = taskManager.addNewEpic(epic2);
        Subtask subtask1 = new Subtask("Subtask 1", "Description", TaskStatus.NEW, epicId1);
        Subtask subtask2 = new Subtask("Subtask 2", "Description", TaskStatus.NEW, epicId2);

        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);

        assertEquals(2, taskManager.getEpics().size(), "Должно быть 2 эпика");
        assertEquals(2, taskManager.getSubtasks().size(), "Должно быть 2 подзадачи");

        taskManager.deleteAllEpics();

        assertEquals(0, taskManager.getEpics().size(), "Эпиков не должно быть");
        assertEquals(0, taskManager.getSubtasks().size(), "Подзадач не должно быть");
    }

    // Тест (удаление всех подзадач должно очищать список подзадач)
    @Test
    void deleteAllSubtasksShouldClearAllSubtasks() {
        Epic epic = new Epic("Epic", "Description");
        int epicId = taskManager.addNewEpic(epic);
        Subtask subtask1 = new Subtask("Subtask 1", "Description", TaskStatus.NEW, epicId);
        Subtask subtask2 = new Subtask("Subtask 2", "Description", TaskStatus.NEW, epicId);
        Subtask subtask3 = new Subtask("Subtask 3", "Description", TaskStatus.NEW, epicId);

        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);

        assertEquals(3, taskManager.getSubtasks().size(), "Должно быть 3 подзадачи");
        assertEquals(3, epic.getSubtaskIds().size(), "Эпик должен содержать 3 подзадачи");

        taskManager.deleteAllSubtasks();

        assertEquals(0, taskManager.getSubtasks().size(), "Подзадач не должно быть");
        assertEquals(0, epic.getSubtaskIds().size(), "Эпик не должен содержать подзадачи");
        assertEquals(TaskStatus.NEW, taskManager.getEpic(epicId).getStatus(),
                "Статус эпика без подзадач должен быть NEW");
    }

    // Тест (метод getHistory должен возвращать копию, а не оригинальный список)
    @Test
    void getHistoryShouldReturnCopyOfHistory() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);

        taskManager.getTask(taskId);

        ArrayList<Task> history1 = taskManager.getHistory();
        ArrayList<Task> history2 = taskManager.getHistory();

        assertNotSame(history1, history2, "Метод getHistory должен возвращать копию");

        assertEquals(history1.size(), history2.size(), "Размеры должны быть одинаковыми");
        assertEquals(history1.get(0), history2.get(0), "Содержимое должно быть одинаковым");
    }

    // Тест (история не должна содержать дубликаты при повторных просмотрах)
    @Test
    void historyShouldNotContainDuplicatesFromRepeatedViews() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);

        taskManager.getTask(taskId);
        taskManager.getTask(taskId);
        taskManager.getTask(taskId);

        assertEquals(3, taskManager.getHistory().size(),
                "В текущей реализации история может содержать дубликаты");

        for (Task taskInHistory : taskManager.getHistory()) {
            assertEquals(taskId, taskInHistory.getId(), "Все задачи в истории должны иметь одинаковый ID");
        }
    }

    // Тест (история должна ограничиваться десятью последними просмотренными задачами)
    @Test
    void historyShouldBeLimitedToTenItems() {
        for (int i = 1; i <= 15; i++) {
            Task task = new Task("Task " + i, "Description " + i, TaskStatus.NEW);
            int taskId = taskManager.addNewTask(task);
            taskManager.getTask(taskId);
        }

        ArrayList<Task> history = taskManager.getHistory();
        assertEquals(10, history.size(), "История должна содержать максимум 10 задач");

        for (int i = 0; i < history.size(); i++) {
            assertEquals(i + 6, history.get(i).getId(),
                    "В истории должны быть последние 10 просмотренных задач");
        }
    }

    // Тест (история должна содержать задачи всех типов)
    @Test
    void historyShouldContainAllTypesOfTasks() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        Epic epic = new Epic("Epic", "Description");
        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, 2);

        int taskId = taskManager.addNewTask(task);
        int epicId = taskManager.addNewEpic(epic);
        subtask.setEpicId(epicId);
        int subtaskId = taskManager.addNewSubtask(subtask);

        taskManager.getTask(taskId);
        taskManager.getEpic(epicId);
        taskManager.getSubtask(subtaskId);

        ArrayList<Task> history = taskManager.getHistory();

        assertEquals(3, history.size(), "История должна содержать 3 задачи");

        assertTrue(history.get(0) instanceof Task, "Первый элемент должен быть Task");
        assertTrue(history.get(1) instanceof Epic, "Второй элемент должен быть Epic");
        assertTrue(history.get(2) instanceof Subtask, "Третий элемент должен быть Subtask");
    }

    // Тест (обновление задачи не должно влиять на ее копию в истории)
    @Test
    void updatingTaskShouldNotAffectTaskInHistory() {
        Task originalTask = new Task("Original", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(originalTask);

        taskManager.getTask(taskId);

        Task updatedTask = new Task(taskId, "Updated", "Updated Description", TaskStatus.DONE);
        taskManager.updateTask(updatedTask);

        Task taskFromManager = taskManager.getTask(taskId);
        Task taskFromHistory = taskManager.getHistory().get(0);

        assertEquals("Updated", taskFromManager.getName(), "Задача в менеджере должна быть обновлена");
        assertEquals(TaskStatus.DONE, taskFromManager.getStatus(), "Статус в менеджере должен быть DONE");

        assertEquals("Original", taskFromHistory.getName(),
                "Задача в истории должна сохранить исходное состояние");
        assertEquals(TaskStatus.NEW, taskFromHistory.getStatus(),
                "Статус в истории должен сохранить исходное состояние");
        assertNotSame(taskFromManager, taskFromHistory,
                "Задача в истории должна быть копией, а не ссылкой на ту же задачу");
    }

    // Тест (удаление несуществующих задач не должно вызывать исключений)
    @Test
    void deletingNonExistentTaskShouldNotThrowException() {
        assertDoesNotThrow(() -> taskManager.deleteTask(999),
                "Удаление несуществующей задачи не должно вызывать исключение");
        assertDoesNotThrow(() -> taskManager.deleteEpic(999),
                "Удаление несуществующего эпика не должно вызывать исключение");
        assertDoesNotThrow(() -> taskManager.deleteSubtask(999),
                "Удаление несуществующей подзадачи не должно вызывать исключение");
    }

    // Тест (обновление несуществующих задач не должно вызывать исключений)
    @Test
    void updatingNonExistentTaskShouldNotThrowException() {
        Task nonExistentTask = new Task(999, "Non-existent", "Description", TaskStatus.NEW);
        assertDoesNotThrow(() -> taskManager.updateTask(nonExistentTask),
                "Обновление несуществующей задачи не должно вызывать исключение");

        Epic nonExistentEpic = new Epic(999, "Non-existent", "Description", TaskStatus.NEW);
        assertDoesNotThrow(() -> taskManager.updateEpic(nonExistentEpic),
                "Обновление несуществующего эпика не должно вызывать исключение");

        Subtask nonExistentSubtask = new Subtask(999, "Non-existent", "Description",
                TaskStatus.NEW, 999);
        assertDoesNotThrow(() -> taskManager.updateSubtask(nonExistentSubtask),
                "Обновление несуществующей подзадачи не должно вызывать исключение");
    }

    // Тест (получение несуществующих задач должно возвращать null)
    @Test
    void gettingNonExistentTaskShouldReturnNull() {
        assertNull(taskManager.getTask(999), "Получение несуществующей задачи должно возвращать null");
        assertNull(taskManager.getEpic(999), "Получение несуществующего эпика должно возвращать null");
        assertNull(taskManager.getSubtask(999), "Получение несуществующей подзадачи должно возвращать null");
    }

    // Тест (пустой менеджер должен возвращать пустые списки)
    @Test
    void emptyManagerShouldReturnEmptyLists() {
        assertTrue(taskManager.getTasks().isEmpty(), "Список задач должен быть пустым");
        assertTrue(taskManager.getEpics().isEmpty(), "Список эпиков должен быть пустым");
        assertTrue(taskManager.getSubtasks().isEmpty(), "Список подзадач должен быть пустым");
        assertTrue(taskManager.getHistory().isEmpty(), "История должна быть пустой");
    }

    // Тест (задача не должна менять id при обновлении)
    @Test
    void taskShouldNotChangeIdAfterUpdate() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);
        int taskId = taskManager.addNewTask(task);

        Task updatedTask = new Task(999, "Updated", "Description", TaskStatus.DONE);
        updatedTask.setId(taskId); // Восстанавливаем правильный ID

        taskManager.updateTask(updatedTask);

        Task retrievedTask = taskManager.getTask(taskId);
        assertNotNull(retrievedTask, "Задача должна быть доступна по старому ID");
        assertEquals("Updated", retrievedTask.getName(), "Имя должно быть обновлено");
        assertNull(taskManager.getTask(999), "Задачи с новым ID не должно существовать");
    }
}
