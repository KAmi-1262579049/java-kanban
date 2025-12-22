package managers;

import managers.impl.InMemoryHistoryManager;
import managers.impl.InMemoryTaskManager;

// Класс для создания менеджеров
public class Managers {
    // Метод для получения менеджера задач по умолчанию
    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    // Метод для получения менеджера истории по умолчанию
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}