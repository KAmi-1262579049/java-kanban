package task;

import enums.TaskStatus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для Subtask
class SubtaskTest {

    // Тест (подзадачи наследуют логику сравнения по id от Task)
    @Test
    void subtaskInheritsTaskEquality() {
        Subtask subtask1 = new Subtask(1, "Subtask 1", "Description 1", TaskStatus.NEW, 10);
        Subtask subtask2 = new Subtask(1, "Subtask 2", "Description 2", TaskStatus.DONE, 20);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны");
    }

    // Тест (подзадача не может быть своим же эпиком)
    @Test
    void subtaskCannotBeItsOwnEpic() {
        Subtask subtask = new Subtask(1, "Subtask", "Description", TaskStatus.NEW, 2);

        assertNotEquals(subtask.getId(), subtask.getEpicId(),
                "ID подзадачи не должно совпадать с ID её эпика");
    }

    // Тест (конструктор Subtask должен корректно устанавливать epicId)
    @Test
    void subtaskConstructorShouldSetEpicId() {
        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, 100);

        assertEquals(100, subtask.getEpicId(), "epicId должен быть установлен");
    }

    // Тест (метод setEpicId должен корректно изменять epicId подзадачи)
    @Test
    void setEpicIdShouldWorkCorrectly() {
        Subtask subtask = new Subtask("Subtask", "Description", TaskStatus.NEW, 100);

        subtask.setEpicId(200);

        assertEquals(200, subtask.getEpicId(), "epicId должен быть изменен");
    }

    // Тест (конструктор копирования Subtask должен копировать все поля)
    @Test
    void subtaskCopyConstructorShouldCopyAllFields() {
        Subtask original = new Subtask(1, "Subtask", "Description", TaskStatus.IN_PROGRESS, 100);
        Subtask copy = new Subtask(original);

        assertEquals(original.getId(), copy.getId(), "ID должен быть скопирован");
        assertEquals(original.getName(), copy.getName(), "Имя должно быть скопировано");
        assertEquals(original.getDescription(), copy.getDescription(), "Описание должно быть скопировано");
        assertEquals(original.getStatus(), copy.getStatus(), "Статус должен быть скопирован");
        assertEquals(original.getEpicId(), copy.getEpicId(), "epicId должен быть скопирован");
        assertNotSame(original, copy, "Копия должна быть отдельным объектом");
    }

    // Тест (метод toString() у Subtask должен содержать epicId)
    @Test
    void subtaskToStringShouldContainEpicId() {
        Subtask subtask = new Subtask(1, "Test Subtask", "Test Description",
                TaskStatus.IN_PROGRESS, 100);
        String subtaskString = subtask.toString();

        assertTrue(subtaskString.contains("id=1"), "Должен содержать ID");
        assertTrue(subtaskString.contains("name='Test Subtask'"), "Должен содержать имя");
        assertTrue(subtaskString.contains("description='Test Description'"), "Должен содержать описание");
        assertTrue(subtaskString.contains("status=IN_PROGRESS"), "Должен содержать статус");
        assertTrue(subtaskString.contains("epicId=100"), "Должен содержать epicId");
    }
}
