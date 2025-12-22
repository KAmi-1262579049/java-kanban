package task;

import enums.TaskStatus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для Task
class TaskTest {

    // Тест (задачи с одинаковым id должны быть равны)
    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task(1, "Task 1", "Description 1", TaskStatus.NEW);
        Task task2 = new Task(1, "Task 2", "Description 2", TaskStatus.IN_PROGRESS);

        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны");
        assertEquals(task1.hashCode(), task2.hashCode(),
                "Хэш-коды задач с одинаковым id должны совпадать");
    }

    // Тест (задачи с разным id не должны быть равны)
    @Test
    void tasksWithDifferentIdShouldNotBeEqual() {
        Task task1 = new Task(1, "Task", "Description", TaskStatus.NEW);
        Task task2 = new Task(2, "Task", "Description", TaskStatus.NEW);

        assertNotEquals(task1, task2, "Задачи с разным id не должны быть равны");
    }

    // Тест (конструктор копирования задачи должен корректно копировать все поля)
    @Test
    void taskConstructorShouldSetAllFields() {
        Task original = new Task(1, "Task", "Description", TaskStatus.IN_PROGRESS);
        Task copy = new Task(original);

        assertEquals(original.getId(), copy.getId(), "ID должен быть скопирован");
        assertEquals(original.getName(), copy.getName(), "Имя должно быть скопировано");
        assertEquals(original.getDescription(), copy.getDescription(), "Описание должно быть скопировано");
        assertEquals(original.getStatus(), copy.getStatus(), "Статус должен быть скопирован");
        assertNotSame(original, copy, "Копия должна быть отдельным объектом");
    }

    // Тест (сеттеры задачи должны корректно устанавливать значения полей)
    @Test
    void taskSettersShouldWorkCorrectly() {
        Task task = new Task("Task", "Description", TaskStatus.NEW);

        task.setId(100);
        assertEquals(100, task.getId(), "ID должен быть установлен");

        task.setName("New Name");
        assertEquals("New Name", task.getName(), "Имя должно быть изменено");

        task.setDescription("New Description");
        assertEquals("New Description", task.getDescription(), "Описание должно быть изменено");

        task.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, task.getStatus(), "Статус должен быть изменен");
    }

    // Тест (метод toString должен включать все поля задачи)
    @Test
    void toStringShouldContainAllFields() {
        Task task = new Task(1, "Test Task", "Test Description", TaskStatus.IN_PROGRESS);
        String taskString = task.toString();

        assertTrue(taskString.contains("id=1"), "Должен содержать ID");
        assertTrue(taskString.contains("name='Test Task'"), "Должен содержать имя");
        assertTrue(taskString.contains("description='Test Description'"), "Должен содержать описание");
        assertTrue(taskString.contains("status=IN_PROGRESS"), "Должен содержать статус");
    }
}