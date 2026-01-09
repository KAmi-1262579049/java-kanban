package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для проверки функциональности класса Task
class TaskTest {
    // Тест, проверяющий равенство задач с одинаковым id
    @Test
    void testTasksWithSameIdAreEqual() {
        Task task1 = new Task(1, "Task 1", "Description 1", TaskStatus.NEW);
        Task task2 = new Task(1, "Task 2", "Description 2", TaskStatus.DONE);

        assertEquals(task1, task2);
    }

    // Тест, проверяющий концепцию "иммутабельности" при сравнении задач
    @Test
    void testTaskImmutability() {
        Task original = new Task("Original", "Description");
        original.setId(1);

        Task copy = new Task(1, "Copy", "Description", TaskStatus.NEW);

        assertEquals(original, copy);
    }
}
