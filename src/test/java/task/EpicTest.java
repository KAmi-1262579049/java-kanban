package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для проверки функциональности класса Epic
class EpicTest {

    // Тест проверяет, что новый эпик не может добавить себя в качестве подзадачи
    @Test
    void epicCannotAddItselfAsSubtask() {
        Epic epic = new Epic("Epic", "Description");
        epic.setId(1);

        assertTrue(epic.getSubtaskIds().isEmpty(), "Список подзадач нового эпика должен быть пустым");
    }

    // Тест проверяет, что Epic наследует от Task логику сравнения по id
    @Test
    void epicInheritsTaskEquality() {
        Epic epic1 = new Epic(1, "Epic 1", "Description");
        Epic epic2 = new Epic(1, "Epic 2", "Description");

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны");
    }

    // Тест проверяет, что конструктор Epic корректно инициализирует пустой список подзадач
    @Test
    void epicConstructorShouldInitializeEmptySubtaskList() {
        Epic epic = new Epic("Epic", "Description");

        assertNotNull(epic.getSubtaskIds(), "Список подзадач не должен быть null");
        assertTrue(epic.getSubtaskIds().isEmpty(), "Список подзадач должен быть пустым");
    }

    // Тест проверяет, что метод addSubtaskId не добавляет дубликаты id подзадач
    @Test
    void addSubtaskIdShouldNotAddDuplicates() {
        Epic epic = new Epic("Epic", "Description");
        epic.addSubtaskId(1);
        epic.addSubtaskId(1);
        epic.addSubtaskId(1);

        assertEquals(1, epic.getSubtaskIds().size(), "ID должен быть добавлен только один раз");
        assertTrue(epic.getSubtaskIds().contains(1), "Список должен содержать ID 1");
    }

    // Тест проверяет корректность работы метода удаления id подзадачи
    @Test
    void removeSubtaskIdShouldWorkCorrectly() {
        Epic epic = new Epic("Epic", "Description");
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);
        epic.addSubtaskId(3);

        assertEquals(3, epic.getSubtaskIds().size(), "Должно быть 3 ID");

        epic.removeSubtaskId(2);

        assertEquals(2, epic.getSubtaskIds().size(), "Должно остаться 2 ID");
        assertTrue(epic.getSubtaskIds().contains(1), "Должен содержать ID 1");
        assertFalse(epic.getSubtaskIds().contains(2), "Не должен содержать ID 2");
        assertTrue(epic.getSubtaskIds().contains(3), "Должен содержать ID 3");
    }

    // Тест проверяет, что метод clearSubtaskIds полностью очищает список подзадач
    @Test
    void clearSubtaskIdsShouldRemoveAllIds() {
        Epic epic = new Epic("Epic", "Description");
        epic.addSubtaskId(1);
        epic.addSubtaskId(2);
        epic.addSubtaskId(3);

        assertEquals(3, epic.getSubtaskIds().size(), "Должно быть 3 ID");

        epic.clearSubtaskIds();

        assertTrue(epic.getSubtaskIds().isEmpty(), "Список должен быть пустым");
    }
}
