import manager.TaskManager;
import task.Task;
import task.TaskStatus;
import task.Epic;
import task.Subtask;

// Главный класс приложения для тестирования функциональности трекера задач
public class Main {
    public static void main(String[] args) {
        // Вывод приветственного сообщения
        System.out.println("Тестирование трекера задач!");
        System.out.println("============================");

        TaskManager taskManager = new TaskManager();

        // ============================================
        // ТЕСТ 1: СОЗДАНИЕ ОБЫЧНЫХ ЗАДАЧ
        // ============================================
        System.out.println("\n=== ТЕСТ 1: Создание обычных задач ===");

        // Создаем две задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");

        System.out.println("\n=== Создание задач ===");

        // Добавление задач в менеджер
        Task createdTask1 = taskManager.createTask(task1);
        Task createdTask2 = taskManager.createTask(task2);

        // Вывод информации о созданных задачах
        System.out.println("Создана задача 1: " + createdTask1);
        System.out.println("Создана задача 2: " + createdTask2);

        // ============================================
        // ТЕСТ 2: СОЗДАНИЕ ЭПИКОВ И ПОДЗАДАЧ
        // ============================================
        System.out.println("\n=== ТЕСТ 2: Создание эпиков и подзадач ===");

        // Создание эпика с двумя подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic createdEpic1 = taskManager.createEpic(epic1);
        System.out.println("\nСоздан эпик 1: " + createdEpic1);

        // Создание подзадач для первого эпика
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", createdEpic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", createdEpic1.getId());

        // Добавление подзадач в менеджер
        Subtask createdSubtask1 = taskManager.createSubtask(subtask1);
        Subtask createdSubtask2 = taskManager.createSubtask(subtask2);
        System.out.println("Создана подзадача 1: " + createdSubtask1);
        System.out.println("Создана подзадача 2: " + createdSubtask2);

        // Создание второго эпика с одной подзадачей
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Epic createdEpic2 = taskManager.createEpic(epic2);
        System.out.println("\nСоздан эпик 2: " + createdEpic2);

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", createdEpic2.getId());
        Subtask createdSubtask3 = taskManager.createSubtask(subtask3);
        System.out.println("Создана подзадача 3: " + createdSubtask3);

        // ============================================
        // ТЕСТ 3: ВЫВОД ВСЕХ ЗАДАЧ
        // ============================================
        System.out.println("\n=== ТЕСТ 3: Вывод всех задач ===");

        // Получение и вывод всех обычных задач
        System.out.println("\n=== Все задачи ===");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        // Получение и вывод всех эпиков
        System.out.println("\n=== Все эпики ===");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        // Получение и вывод всех подзадач
        System.out.println("\n=== Все подзадачи ===");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        // ============================================
        // ТЕСТ 4: ИЗМЕНЕНИЕ СТАТУСОВ
        // ============================================
        System.out.println("\n=== ТЕСТ 4: Изменения статусов задач ===");

        // Изменение статуса задачи
        createdTask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(createdTask1);
        System.out.println("Задача 1 после изменения статуса: " + taskManager.getTaskById(createdTask1.getId()));

        // Изменение статусов подзадач первого эпика
        System.out.println("\nИзменяем статусы подзадач эпика 1:");
        createdSubtask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(createdSubtask1);

        createdSubtask2.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(createdSubtask2);

        // Проверка изменения статуса эпика
        System.out.println("Эпик 1 после изменения статусов подзадач: " + taskManager.getEpicById(createdEpic1.getId()));

        // Изменение статуса подзадачи второго эпика
        createdSubtask3.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(createdSubtask3);

        // Проверка статуса второго эпика
        System.out.println("Эпик 2 после изменения статуса подзадачи: " + taskManager.getEpicById(createdEpic2.getId()));

        // Получение подзадачи эпика
        System.out.println("\n=== Подзадачи эпика 1 ===");
        for (Subtask subtask : taskManager.getSubtasksByEpicId(createdEpic1.getId())) {
            System.out.println(subtask);
        }

        // Проверка истории просмотров
        System.out.println("\n=== История просмотров ===");
        System.out.println("Задач в истории: " + taskManager.getHistory().size());
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        // Удаление одной задачи и одного эпика
        System.out.println("\n=== Удаление задачи и эпика ===");
        taskManager.deleteTaskById(createdTask1.getId());
        taskManager.deleteEpicById(createdEpic1.getId());

        // Проверка оставшихся задач
        System.out.println("\nОставшиеся задачи:");
        for (Task task : taskManager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("\nОставшиеся эпики:");
        for (Epic epic : taskManager.getAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("\nОставшиеся подзадачи:");
        for (Subtask subtask : taskManager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("\n=== Итоговая история просмотров ===");
        System.out.println("Задач в истории: " + taskManager.getHistory().size());
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        // Тестирование удаления всех задач
        System.out.println("\n=== Удаление всех задач ===");
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();
        taskManager.deleteAllSubtasks();

        System.out.println("Задачи после удаления всех: " + taskManager.getAllTasks().size());
        System.out.println("Эпики после удаления всех: " + taskManager.getAllEpics().size());
        System.out.println("Подзадачи после удаления всех: " + taskManager.getAllSubtasks().size());
    }
}
