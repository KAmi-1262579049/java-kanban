package managers;

import task.Task;
import task.Epic;
import task.Subtask;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ManagersTest {

    // Тест (метод getDefault() должен возвращать проинициализированный и готовый к работе менеджер задач)
    @Test
    void getDefaultShouldReturnInitializedTaskManager() {
        TaskManager taskManager = Managers.getDefault();

        assertNotNull(taskManager, "Фабричный метод getDefault() должен возвращать не-null объект");
        assertTrue(taskManager instanceof TaskManager,
                "Возвращаемый объект должен быть экземпляром класса, реализующего TaskManager");

        ArrayList<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Метод getTasks() не должен возвращать null");

        ArrayList<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Метод getEpics() не должен возвращать null");

        ArrayList<Subtask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks, "Метод getSubtasks() не должен возвращать null");

        ArrayList<Task> history = taskManager.getHistory();
        assertNotNull(history, "Метод getHistory() не должен возвращать null");

        assertTrue(tasks.isEmpty(), "Новый менеджер должен иметь пустой список задач");
        assertTrue(epics.isEmpty(), "Новый менеджер должен иметь пустой список эпиков");
        assertTrue(subtasks.isEmpty(), "Новый менеджер должен иметь пустой список подзадач");
        assertTrue(history.isEmpty(), "Новый менеджер должен иметь пустую историю");
    }

    // Тест (метод getDefaultHistory должен возвращать инициализированный менеджер истории)
    @Test
    void getDefaultHistoryShouldReturnInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager, "Метод getDefaultHistory() не должен возвращать null");
        assertTrue(historyManager instanceof HistoryManager,
                "Возвращаемый объект должен реализовывать интерфейс HistoryManager");

        ArrayList<Task> history = historyManager.getHistory();

        assertNotNull(history, "Метод getHistory() не должен возвращать null");

        assertTrue(history.isEmpty(),
                "Новый менеджер истории должен возвращать пустой список");
    }

    //  Тест (менеджеры должны возвращать разные экземпляры)
    @Test
    void managersShouldReturnDifferentInstances() {
        TaskManager taskManager1 = Managers.getDefault();
        TaskManager taskManager2 = Managers.getDefault();

        HistoryManager historyManager1 = Managers.getDefaultHistory();
        HistoryManager historyManager2 = Managers.getDefaultHistory();

        assertNotSame(taskManager1, taskManager2,
                "getDefault() должен возвращать разные экземпляры TaskManager");
        assertNotSame(historyManager1, historyManager2,
                "getDefaultHistory() должен возвращать разные экземпляры HistoryManager");
    }
}
