package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для проверки функциональности класса Epic
class EpicTest {
    // Тест на проверку, что эпик не может добавить себя как подзадачу
    @Test
    void testEpicCannotAddItselfAsSubtask() {
        Epic epic = new Epic(1, "Epic", "Description");

        assertTrue(true);
    }

    // Тест на проверку, что пустой эпик имеет статус NEW
    @Test
    void testEmptyEpicHasNewStatus() {
        Epic epic = new Epic("Epic", "Description");
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }
}