package task;

import enums.TaskStatus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Тестовый класс для Epic
class EpicTest {

    // Тест (эпик не должен добавлять себя как подзадачу)
    @Test
    void epicCannotAddItselfAsSubtask() {
        Epic epic = new Epic("Epic", "Description");
        epic.setId(1);

        assertTrue(epic.getSubtaskIds().isEmpty(),
                "Список подзадач нового эпика должен быть пустым");
    }

    // Тест (эпики наследуют логику сравнения по id от Task)
    @Test
    void epicInheritsTaskEquality() {
        Epic epic1 = new Epic(1, "Epic 1", "Description 1", TaskStatus.NEW);
        Epic epic2 = new Epic(1, "Epic 2", "Description 2", TaskStatus.IN_PROGRESS);

        assertEquals(epic1, epic2, "Эпики с одинаковым id должны быть равны");
    }

    // Тест (конструктор эпика должен инициализировать пустой список подзадач)
    @Test
    void epicConstructorShouldInitializeEmptySubtaskList() {
        Epic epic = new Epic("Epic", "Description");

        assertNotNull(epic.getSubtaskIds(), "Список подзадач не должен быть null");
        assertTrue(epic.getSubtaskIds().isEmpty(), "Список подзадач должен быть пустым");
    }

    // Тест (добавление id подзадачи не должно добавлять дубликаты)
    @Test
    void addSubtaskIdShouldNotAddDuplicates() {
        Epic epic = new Epic("Epic", "Description");
        epic.addSubtaskId(1);
        epic.addSubtaskId(1);
        epic.addSubtaskId(1);

        assertEquals(1, epic.getSubtaskIds().size(), "ID должен быть добавлен только один раз");
        assertTrue(epic.getSubtaskIds().contains(1), "Список должен содержать ID 1");
    }

    // Тест (удаление id подзадачи должно работать корректно)
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

    // Тест (очистка списка подзадач должна удалять все id)
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

    // Тест (конструктор копирования эпика должен копировать id подзадач)
    @Test
    void epicCopyConstructorShouldCopySubtaskIds() {
        Epic original = new Epic(1, "Epic", "Description", TaskStatus.NEW);
        original.addSubtaskId(10);
        original.addSubtaskId(20);
        original.addSubtaskId(30);
        Epic copy = new Epic(original);

        assertEquals(original.getId(), copy.getId(), "ID должен быть скопирован");
        assertEquals(original.getName(), copy.getName(), "Имя должно быть скопировано");
        assertEquals(original.getDescription(), copy.getDescription(), "Описание должно быть скопировано");
        assertEquals(original.getStatus(), copy.getStatus(), "Статус должен быть скопирован");

        assertEquals(original.getSubtaskIds().size(), copy.getSubtaskIds().size(),
                "Размер списка подзадач должен быть одинаковым");
        assertTrue(copy.getSubtaskIds().contains(10), "Должен содержать ID 10");
        assertTrue(copy.getSubtaskIds().contains(20), "Должен содержать ID 20");
        assertTrue(copy.getSubtaskIds().contains(30), "Должен содержать ID 30");

        assertNotSame(original, copy, "Копия должна быть отдельным объектом");
        assertNotSame(original.getSubtaskIds(), copy.getSubtaskIds(),
                "Списки подзадач должны быть разными объектами");

        original.clearSubtaskIds();
        assertEquals(3, copy.getSubtaskIds().size(),
                "Копия не должна измениться при изменении оригинала");
    }
}