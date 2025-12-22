package task;

import java.util.Objects;

// Класс Task представляет базовую задачу в трекере
public class Task {
    protected int id; // Уникальный идентификатор задачи
    protected String name; // Название задачи
    protected String description; // Подробное описание задачи
    protected TaskStatus status; // Текущий статус задачи
    protected TaskType type; // Тип задачи

    // Конструктор класса для создания задачи (без id)
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.type = TaskType.TASK;
    }
    // Конструктор класса для создания задачи (с известным id)
    public Task(int id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = TaskType.TASK;
    }

    // Геттер для получения id задачи
    public int getId() {
        return id;
    }

    // Сеттер для установки id задачи
    public void setId(int id) {
        this.id = id;
    }

    // Геттер для получения названия задачи
    public String getName() {
        return name;
    }

    // Сеттер для изменения названия задачи
    public void setName(String name) {
        this.name = name;
    }

    // Геттер для получения описания задачи
    public String getDescription() {
        return description;
    }

    // Сеттер для изменения описания задачи
    public void setDescription(String description) {
        this.description = description;
    }

    // Геттер для получения текущего статуса задачи
    public TaskStatus getStatus() {
        return status;
    }

    // Сеттер для изменения статуса задачи
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // Геттер для получения типа задачи
    public TaskType getType() {
        return type;
    }

    // Сеттер для изменения типа задачи
    public void setType(TaskType type) {
        this.type = type;
    }

    // Переопределение метода equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    // Переопределение метода hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "task.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}