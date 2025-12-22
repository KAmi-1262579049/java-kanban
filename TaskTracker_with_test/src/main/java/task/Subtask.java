package task;

import enums.TaskStatus;

// Класс подзадачи - наследуется от Task
public class Subtask extends Task {
    private Integer epicId; // id эпика, к которому относится подзадача

    // Конструктор для создания новой подзадачи
    public Subtask(String name, String description, TaskStatus status, Integer epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    // Конструктор для создания подзадачи с заданными параметрами
    public Subtask(Integer id, String name, String description, TaskStatus status, Integer epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    // Конструктор копирования
    public Subtask(Subtask subtask) {
        super(subtask);
        this.epicId = subtask.epicId;
    }

    // Геттер для id эпика
    public Integer getEpicId() {
        return epicId;
    }

    // Сеттер для id эпика
    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}