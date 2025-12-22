package task;

import enums.TaskStatus;
import java.util.Objects;

// Базовый класс задачи
public class Task {
    private Integer id; // Уникальный идентификатор задачи
    private String name; // Название задачи
    private String description; // Описание задачи
    private TaskStatus status; // Статус задачи

    // Конструктор для создания новой задачи без id
    public Task(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Конструктор для создания задачи с заданным id
    public Task(Integer id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Конструктор копирования
    public Task(Task task) {
        this.id = task.id;
        this.name = task.name;
        this.description = task.description;
        this.status = task.status;
    }

    // Геттер для id
    public Integer getId() {
        return id;
    }

    // Сеттер для id
    public void setId(Integer id) {
        this.id = id;
    }

    // Геттер для имени
    public String getName() {
        return name;
    }

    // Сеттер для имени
    public void setName(String name) {
        this.name = name;
    }

    // Геттер для описания
    public String getDescription() {
        return description;
    }

    // Сеттер для описания
    public void setDescription(String description) {
        this.description = description;
    }

    // Геттер для статуса
    public TaskStatus getStatus() {
        return status;
    }

    // Сеттер для статуса
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // Переопределение метода equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return Objects.equals(id, task.id);
    }

    // Переопределение метода hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}