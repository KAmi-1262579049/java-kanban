import managers.Managers;
import managers.TaskManager;
import task.Task;
import task.Epic;
import task.Subtask;
import enums.TaskStatus;

// Главный класс приложения
public class Main {
    public static void main(String[] args) {
        // Вывод приветственного сообщения
        System.out.println("=== Тестирование трекера задач! ===");
        System.out.println("====================================\n");

        TaskManager taskManager = Managers.getDefault();

        // ============================================
        // ТЕСТ 1: СОЗДАНИЕ ОБЫЧНЫХ ЗАДАЧ
        // ============================================
        System.out.println("=== ТЕСТ 1: Создание обычных задач ===");

        // Создание двух задач
        Task task1 = new Task("Задача 1", "Описание задачи 1", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", TaskStatus.NEW);

        // Добавление задач в менеджер
        int taskId1 = taskManager.addNewTask(task1);
        int taskId2 = taskManager.addNewTask(task2);

        // Вывод информации о созданных задачах
        System.out.println("Создана задача 1 (ID: " + taskId1 + "): " + taskManager.getTask(taskId1));
        System.out.println("Создана задача 2 (ID: " + taskId2 + "): " + taskManager.getTask(taskId2));

        // ============================================
        // ТЕСТ 2: СОЗДАНИЕ ЭПИКОВ И ПОДЗАДАЧ
        // ============================================
        System.out.println("\n=== ТЕСТ 2: Создание эпиков и подзадач ===");

        // Создание первого эпика
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        int epicId1 = taskManager.addNewEpic(epic1);
        System.out.println("Создан эпик 1 (ID: " + epicId1 + "): " + taskManager.getEpic(epicId1));

        // Создание подзадач для первого эпика
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, epicId1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, epicId1);

        // Добавление подзадач в менеджер
        int subtaskId1 = taskManager.addNewSubtask(subtask1);
        int subtaskId2 = taskManager.addNewSubtask(subtask2);
        System.out.println("Создана подзадача 1 (ID: " + subtaskId1 + "): " + taskManager.getSubtask(subtaskId1));
        System.out.println("Создана подзадача 2 (ID: " + subtaskId2 + "): " + taskManager.getSubtask(subtaskId2));

        // Создание второго эпика
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        int epicId2 = taskManager.addNewEpic(epic2);
        System.out.println("Создан эпик 2 (ID: " + epicId2 + "): " + taskManager.getEpic(epicId2));

        // Создание подзадачи для второго эпика
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", TaskStatus.NEW, epicId2);
        int subtaskId3 = taskManager.addNewSubtask(subtask3);
        System.out.println("Создана подзадача 3 (ID: " + subtaskId3 + "): " + taskManager.getSubtask(subtaskId3));

        // ============================================
        // ТЕСТ 3: ВЫВОД ВСЕХ ЗАДАЧ
        // ============================================
        System.out.println("\n=== ТЕСТ 3: Вывод всех задач ===");

        // Получение и вывод всех обычных задач
        System.out.println("\n=== Все задачи ===");
        if (taskManager.getTasks().isEmpty()) {
            System.out.println("(нет задач)");
        } else {
            for (Task task : taskManager.getTasks()) {
                System.out.println(task);
            }
        }

        // Получение и вывод всех эпиков
        System.out.println("\n=== Все эпики ===");
        if (taskManager.getEpics().isEmpty()) {
            System.out.println("(нет эпиков)");
        } else {
            for (Epic epic : taskManager.getEpics()) {
                System.out.println(epic);
                // Вывод подзадач каждого эпика
                var epicSubtasks = taskManager.getEpicSubtasks(epic.getId());
                if (!epicSubtasks.isEmpty()) {
                    System.out.println("  Подзадачи:");
                    for (Subtask subtask : epicSubtasks) {
                        System.out.println("    - " + subtask);
                    }
                }
            }
        }

        // Получение и вывод всех подзадач
        System.out.println("\n=== Все подзадачи ===");
        if (taskManager.getSubtasks().isEmpty()) {
            System.out.println("(нет подзадач)");
        } else {
            for (Subtask subtask : taskManager.getSubtasks()) {
                System.out.println(subtask);
            }
        }

        // ============================================
        // ТЕСТ 4: ИЗМЕНЕНИЕ СТАТУСОВ
        // ============================================
        System.out.println("\n=== ТЕСТ 4: Изменение статусов задач ===");

        // Изменение статуса первой задачи на IN_PROGRESS
        System.out.println("\n1. Изменяем статус задачи 1 на IN_PROGRESS:");
        Task updatedTask1 = new Task(taskId1, "Задача 1", "Описание задачи 1", TaskStatus.IN_PROGRESS);
        taskManager.updateTask(updatedTask1);
        System.out.println("   Задача 1 после изменения: " + taskManager.getTask(taskId1));

        // Изменение статуса второй задачи на DONE
        System.out.println("\n2. Изменяем статус задачи 2 на DONE:");
        Task updatedTask2 = new Task(taskId2, "Задача 2", "Описание задачи 2", TaskStatus.DONE);
        taskManager.updateTask(updatedTask2);
        System.out.println("   Задача 2 после изменения: " + taskManager.getTask(taskId2));

        // Изменение статусов подзадач первого эпика
        System.out.println("\n3. Изменяем статусы подзадач эпика 1:");

        System.out.println("   - Подзадача 1 -> IN_PROGRESS:");
        Subtask updatedSubtask1 = new Subtask(subtaskId1, "Подзадача 1", "Описание подзадачи 1",
                TaskStatus.IN_PROGRESS, epicId1);
        taskManager.updateSubtask(updatedSubtask1);
        System.out.println("     " + taskManager.getSubtask(subtaskId1));

        System.out.println("   - Подзадача 2 -> DONE:");
        Subtask updatedSubtask2 = new Subtask(subtaskId2, "Подзадача 2", "Описание подзадачи 2",
                TaskStatus.DONE, epicId1);
        taskManager.updateSubtask(updatedSubtask2);
        System.out.println("     " + taskManager.getSubtask(subtaskId2));

        // Проверка изменения статуса первого эпика (должен стать IN_PROGRESS)
        System.out.println("\n4. Проверяем статус эпика 1 (должен быть IN_PROGRESS):");
        Epic epic1AfterUpdate = taskManager.getEpic(epicId1);
        System.out.println("   Эпик 1: " + epic1AfterUpdate);
        System.out.println("   Статус: " + epic1AfterUpdate.getStatus());

        // Изменение статуса подзадачи второго эпика на DONE
        System.out.println("\n5. Изменяем статус подзадачи 3 на DONE:");
        Subtask updatedSubtask3 = new Subtask(subtaskId3, "Подзадача 3", "Описание подзадачи 3",
                TaskStatus.DONE, epicId2);
        taskManager.updateSubtask(updatedSubtask3);
        System.out.println("   Подзадача 3: " + taskManager.getSubtask(subtaskId3));

        // Проверка статуса второго эпика (должен стать DONE)
        System.out.println("\n6. Проверяем статус эпика 2 (должен быть DONE):");
        Epic epic2AfterUpdate = taskManager.getEpic(epicId2);
        System.out.println("   Эпик 2: " + epic2AfterUpdate);
        System.out.println("   Статус: " + epic2AfterUpdate.getStatus());

        // ============================================
        // ТЕСТ 5: ИСТОРИЯ ПРОСМОТРОВ
        // ============================================
        System.out.println("\n=== ТЕСТ 5: История просмотров ===");

        // Просмотр некоторых задач для добавления их в историю
        System.out.println("\nПросматриваем задачи для истории:");
        System.out.println("1. Задача 1: " + taskManager.getTask(taskId1).getName());
        System.out.println("2. Эпик 1: " + taskManager.getEpic(epicId1).getName());
        System.out.println("3. Подзадача 1: " + taskManager.getSubtask(subtaskId1).getName());
        System.out.println("4. Задача 2: " + taskManager.getTask(taskId2).getName());
        System.out.println("5. Эпик 2: " + taskManager.getEpic(epicId2).getName());

        // Вывод истории просмотров
        System.out.println("\n=== История просмотров (последние 10) ===");
        var history = taskManager.getHistory();
        if (history.isEmpty()) {
            System.out.println("(история пуста)");
        } else {
            for (int i = 0; i < history.size(); i++) {
                Task task = history.get(i);
                String type = task instanceof Epic ? "[ЭПИК] " :
                        task instanceof Subtask ? "[ПОДЗАДАЧА] " : "[ЗАДАЧА] ";
                System.out.println((i + 1) + ". " + type + task.getName() +
                        " (ID: " + task.getId() + ", статус: " + task.getStatus() + ")");
            }
        }
        System.out.println("Всего в истории: " + history.size() + " задач");

        // ============================================
        // ТЕСТ 6: УДАЛЕНИЕ ЗАДАЧ
        // ============================================
        System.out.println("\n=== ТЕСТ 6: Удаление задач ===");

        // Удаление задачи 1
        System.out.println("\n1. Удаляем задачу 1 (ID: " + taskId1 + "):");
        taskManager.deleteTask(taskId1);
        System.out.println("   Задача удалена. Осталось задач: " + taskManager.getTasks().size());

        // Проверка, что задача удалилась из истории
        System.out.println("   Задач в истории после удаления: " + taskManager.getHistory().size());

        // Удаление эпика 1 (должны удалиться и его подзадачи)
        System.out.println("\n2. Удаляем эпик 1 (ID: " + epicId1 + "):");
        System.out.println("   Подзадач перед удалением: " + taskManager.getSubtasks().size());
        taskManager.deleteEpic(epicId1);
        System.out.println("   Эпик удален. Осталось эпиков: " + taskManager.getEpics().size());
        System.out.println("   Подзадач после удаления: " + taskManager.getSubtasks().size());

        // Проверка истории после удалений
        System.out.println("\n3. Проверяем историю после удалений:");
        System.out.println("   Задач в истории: " + taskManager.getHistory().size());
        for (int i = 0; i < taskManager.getHistory().size(); i++) {
            Task task = taskManager.getHistory().get(i);
            System.out.println("   " + (i + 1) + ". " + task.getName() + " (ID: " + task.getId() + ")");
        }

        // ============================================
        // ТЕСТ 7: МАССОВОЕ УДАЛЕНИЕ
        // ============================================
        System.out.println("\n=== ТЕСТ 7: Массовое удаление ===");

        // Добавление еще нескольких задач для демонстрации массового удаления
        System.out.println("\n1. Добавляем дополнительные задачи для теста:");
        Task task3 = new Task("Задача 3", "Дополнительная задача", TaskStatus.NEW);
        Task task4 = new Task("Задача 4", "Еще одна задача", TaskStatus.NEW);
        int taskId3 = taskManager.addNewTask(task3);
        int taskId4 = taskManager.addNewTask(task4);
        System.out.println("   Добавлены задачи 3 и 4");

        // Удаление всех задач
        System.out.println("\n2. Удаляем ВСЕ задачи:");
        System.out.println("   Задач перед удалением: " + taskManager.getTasks().size());
        taskManager.deleteAllTasks();
        System.out.println("   Задач после удаления: " + taskManager.getTasks().size());

        // Удаление всех эпиков
        System.out.println("\n3. Удаляем ВСЕ эпики:");
        System.out.println("   Эпиков перед удалением: " + taskManager.getEpics().size());
        System.out.println("   Подзадач перед удалением: " + taskManager.getSubtasks().size());
        taskManager.deleteAllEpics(); // Это удалит и все подзадачи
        System.out.println("   Эпиков после удаления: " + taskManager.getEpics().size());
        System.out.println("   Подзадач после удаления: " + taskManager.getSubtasks().size());

        // Удаление всех подзадач
        System.out.println("\n4. Удаляем ВСЕ подзадачи:");
        taskManager.deleteAllSubtasks();
        System.out.println("   Подзадач после удаления: " + taskManager.getSubtasks().size());

        // ============================================
        // ТЕСТ 8: ИТОГОВАЯ ПРОВЕРКА
        // ============================================
        System.out.println("\n=== ТЕСТ 8: Итоговая проверка ===");

        // Проверка на то, что все очищено
        System.out.println("\nИтоговое состояние трекера:");
        System.out.println("• Задач: " + taskManager.getTasks().size());
        System.out.println("• Эпиков: " + taskManager.getEpics().size());
        System.out.println("• Подзадач: " + taskManager.getSubtasks().size());
        System.out.println("• Задач в истории: " + taskManager.getHistory().size());

        // Создание одной задачи для демонстрации, что трекер работает
        System.out.println("\nСоздаем финальную тестовую задачу:");
        Task finalTask = new Task("Финальная задача", "Демонстрация работы трекера", TaskStatus.NEW);
        int finalTaskId = taskManager.addNewTask(finalTask);
        System.out.println("Создана задача: " + taskManager.getTask(finalTaskId));

        System.out.println("\n=== Тестирование завершено успешно! ===");
    }
}