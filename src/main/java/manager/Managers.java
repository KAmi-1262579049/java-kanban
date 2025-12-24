package manager;

// Класс для создания и управления менеджерами задач
public class Managers {
    // Метод для получения реализации менеджера задач по умолчанию
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    // Метод для получения реализации менеджера истории по умолчанию
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}