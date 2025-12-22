package task;

import enums.TaskStatus;
import java.util.ArrayList;

// Класс эпика наследуется от Task
public class Epic extends Task {
    private ArrayList<Integer> subtaskIds; // Список id подзадач, которые входят в эпик

    // Конструктор для создания нового эпика
    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.subtaskIds = new ArrayList<>();
    }

    // Конструктор для создания эпика с заданными параметрами
    public Epic(Integer id, String name, String description, TaskStatus status) {
        super(id, name, description, status);
        this.subtaskIds = new ArrayList<>();
    }

    // Конструктор копирования
    public Epic(Epic epic) {
        super(epic);
        this.subtaskIds = new ArrayList<>(epic.subtaskIds);
    }

    // Геттер для списка id подзадач
    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    // Метод для добавления id подзадачи в эпик
    public void addSubtaskId(Integer subtaskId) {
        if (!subtaskIds.contains(subtaskId)) {
            subtaskIds.add(subtaskId);
        }
    }

    // Метод для удаления id подзадачи из эпика
    public void removeSubtaskId(Integer subtaskId) {
        subtaskIds.remove(subtaskId);
    }

    // Метод для очистки списка подзадач
    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}