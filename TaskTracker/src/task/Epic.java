package task;

import java.util.ArrayList;

// Класс Epic наследуется от Task и представляет сложную задачу, состоящую из подзадач
public class Epic extends Task {
    private ArrayList<Integer> subtaskIds; // Список идентификаторов подзадач

    // Конструктор для создания нового эпика (без id)
    public Epic(String name, String description) {
        super(name, description); // Вызов конструктора родительского класса
        this.subtaskIds = new ArrayList<>();
        this.type = TaskType.EPIC;
    }

    // Конструктор для создания эпика с известным id
    public Epic(int id, String name, String description) {
        super(id, name, description, TaskStatus.NEW); // Вызов конструктора родительского класса с указанием id
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

    // Метод для очистки списка идентификаторов подзадач
    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "task.Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
