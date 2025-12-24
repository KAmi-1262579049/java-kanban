package task;

import java.util.ArrayList;

// Класс эпика
public class Epic extends Task {
    private ArrayList<Integer> subtaskIds; // Список идентификаторов подзадач этого эпика

    // Конструктор для создания нового эпика без указания id
    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIds = new ArrayList<>();
        this.type = TaskType.EPIC;
        this.status = TaskStatus.NEW; // Статус эпика всегда рассчитывается
    }

    // Конструктор для создания эпика с известным id
    public Epic(int id, String name, String description) {
        super(id, name, description, TaskStatus.NEW);
        this.subtaskIds = new ArrayList<>();
        this.type = TaskType.EPIC;
    }

    // Геттер для получения списка идентификаторов подзадач
    public ArrayList<Integer> getSubtaskIds() {
        return new ArrayList<>(subtaskIds);
    }

    // Метод для добавления идентификатора подзадачи в эпик
    public void addSubtaskId(int subtaskId) {
        if (!subtaskIds.contains(subtaskId)) {
            subtaskIds.add(subtaskId);
        }
    }

    // Метод для удаления идентификатора подзадачи из эпика
    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove(Integer.valueOf(subtaskId));
    }

    // Метод для полной очистки списка подзадач эпика
    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}