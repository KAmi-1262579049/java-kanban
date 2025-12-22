package managers.impl;

import managers.HistoryManager;
import task.Task;
import enums.TaskStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

// Тестовый класс для InMemoryHistoryManager
class InMemoryHistoryManagerTest {
    private HistoryManager historyManager; // Поле для хранения менеджера истории
    private Task task; // Поле для хранения тестовой задачи

    // Метод, выполняемый перед каждым тестом
    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task = new Task(1, "Test task", "Test description", TaskStatus.NEW);
    }

    // Тест добавления задачи в историю
    @Test
    void add() {
        historyManager.add(task);

        final ArrayList<Task> history = historyManager.getHistory();

        assertNotNull(history, "История не должна быть null");
        assertEquals(1, history.size(), "История должна содержать одну задачу");
        assertEquals(task, history.get(0), "Задача в истории должна соответствовать добавленной");
    }

    // Тест (история не должна превышать максимальный размер)
    @Test
    void historyShouldNotExceedMaxSize() {
        for (int i = 1; i <= 15; i++) {
            Task task = new Task(i, "Task " + i, "Description", TaskStatus.NEW);
            historyManager.add(task);
        }

        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(10, history.size(), "История не должна превышать максимальный размер");
        assertEquals(6, history.get(0).getId(), "Первой должна быть задача с id=6");
        assertEquals(15, history.get(9).getId(), "Последней должна быть задача с id=15");
    }

    // Тест (задачи в истории должны сохранять свое состояние)
    @Test
    void tasksInHistoryShouldKeepTheirState() {
        Task originalTask = new Task(1, "Original", "Description", TaskStatus.NEW);

        historyManager.add(originalTask);

        originalTask.setName("Modified");
        originalTask.setStatus(TaskStatus.DONE);

        Task taskInHistory = historyManager.getHistory().get(0);

        assertEquals("Original", taskInHistory.getName(),
                "Имя задачи в истории должно остаться неизменным");
        assertEquals(TaskStatus.NEW, taskInHistory.getStatus(),
                "Статус задачи в истории должен остаться неизменным");
    }

    // Тест удаления задачи из истории
    @Test
    void removeShouldDeleteTaskFromHistory() {
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        assertEquals(3, historyManager.getHistory().size(), "Должно быть 3 задачи в истории");

        historyManager.remove(2);

        ArrayList<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Должно остаться 2 задачи");
        assertEquals(1, history.get(0).getId(), "Первая задача должна быть Task 1");
        assertEquals(3, history.get(1).getId(), "Вторая задача должна быть Task 3");

        historyManager.remove(1);

        history = historyManager.getHistory();
        assertEquals(1, history.size(), "Должна остаться 1 задача");
        assertEquals(3, history.get(0).getId(), "Оставшаяся задача должна быть Task 3");

        historyManager.remove(3);
        assertTrue(historyManager.getHistory().isEmpty(), "История должна быть пустой");
    }

    // Тест (удаление несуществующей задачи не должно вызывать исключений)
    @Test
    void removeNonExistentTaskShouldNotThrowException() {
        ArrayList<Task> initialHistory = historyManager.getHistory();

        historyManager.remove(999);

        ArrayList<Task> finalHistory = historyManager.getHistory();

        assertTrue(finalHistory.isEmpty(),
                "История должна остаться пустой после удаления несуществующей задачи");

        assertEquals(initialHistory.size(), finalHistory.size(),
                "Размер истории не должен измениться при удалении несуществующей задачи");
    }

    // Тест (удаление задачи из пустой истории не должно вызывать исключений)
    @Test
    void removeFromEmptyHistoryShouldNotThrowException() {
        assertTrue(historyManager.getHistory().isEmpty(),
                "История должна быть пустой перед выполнением теста");

        historyManager.remove(1);

        assertTrue(historyManager.getHistory().isEmpty(),
                "История должна остаться пустой после удаления из пустой истории");
    }

    // Тест (получение истории из пустого менеджера должно возвращать пустой список)
    @Test
    void getHistoryShouldReturnEmptyListWhenHistoryIsEmpty() {
        ArrayList<Task> history = historyManager.getHistory();

        assertNotNull(history, "Метод getHistory не должен возвращать null");
        assertTrue(history.isEmpty(), "История должна быть пустой");
    }

    // Тест (добавление null задачи должно игнорироваться)
    @Test
    void addingNullTaskShouldBeIgnored() {
        historyManager.add(null);

        assertTrue(historyManager.getHistory().isEmpty(),
                "Добавление null не должно влиять на историю");
    }

    // Тест (история должна сохранять порядок добавления задач)
    @Test
    void historyShouldMaintainInsertionOrder() {
        Task task1 = new Task(1, "Task 1", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task 2", "Description", TaskStatus.NEW);
        Task task3 = new Task(3, "Task 3", "Description", TaskStatus.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);

        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(3, history.size(), "Должно быть 3 задачи");
        assertEquals(1, history.get(0).getId(), "Первая задача должна быть Task 1");
        assertEquals(2, history.get(1).getId(), "Вторая задача должна быть Task 2");
        assertEquals(3, history.get(2).getId(), "Третья задача должна быть Task 3");
    }

    // Тест (история должна автоматически удалять старые задачи при превышении лимита в 10 элементов)
    @Test
    void historyShouldRemoveOldestWhenExceedsLimit() {
        for (int i = 1; i <= 15; i++) {
            Task task = new Task(i, "Task " + i, "Description", TaskStatus.NEW);
            historyManager.add(task);
        }

        ArrayList<Task> history = historyManager.getHistory();

        assertEquals(10, history.size(), "История должна содержать максимум 10 задач");

        for (int i = 0; i < history.size(); i++) {
            assertEquals(i + 6, history.get(i).getId(),
                    "В истории должны быть последние 10 добавленных задач");
        }
    }

    // Тест (удаление задачи должно удалять все ее дубликаты из истории)
    @Test
    void removeFromHistoryWithDuplicates() {
        Task task = new Task(1, "Task", "Description", TaskStatus.NEW);

        historyManager.add(task);
        historyManager.add(task);
        historyManager.add(task);

        assertEquals(3, historyManager.getHistory().size(),
                "Дубликаты должны добавляться в историю");
        historyManager.remove(1);
        assertTrue(historyManager.getHistory().isEmpty(),
                "Все копии задачи должны быть удалены из истории");
    }

    // Тест (история должна хранить копии задач)
    @Test
    void historyShouldContainCopiesNotReferences() {
        Task originalTask = new Task(1, "Original", "Description", TaskStatus.NEW);

        historyManager.add(originalTask);
        originalTask.setName("Modified");
        originalTask.setStatus(TaskStatus.DONE);

        Task taskFromHistory = historyManager.getHistory().get(0);

        assertEquals("Original", taskFromHistory.getName(),
                "Задача в истории должна сохранить исходное имя");
        assertEquals(TaskStatus.NEW, taskFromHistory.getStatus(),
                "Задача в истории должна сохранить исходный статус");
        assertNotSame(originalTask, taskFromHistory,
                "Задача в истории должна быть копией, а не ссылкой");
    }
}