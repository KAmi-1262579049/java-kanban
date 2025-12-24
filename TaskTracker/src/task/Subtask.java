package task;

// Класс Subtask наследуется от Task и представляет подзадачу, входящую в эпик
public class Subtask extends Task {
    private int epicId;

    // Конструктор для создания новой подзадачи (без id)
    public Subtask(String name, String description, int epicId) {
        super(name, description); // Вызов конструктора родительского класса
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    // Конструктор для создания подзадачи с известным id
    public Subtask(int id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status); // Вызов конструкторв родительского класса с указанием id и статуса
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    // Геттер для получения идентификатора эпика
    public int getEpicId() {
        return epicId;
    }

    // Сеттер для изменения идентификатора эпика
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    // Переопределение метода toString для удобного вывода информации о подзадаче
    @Override
    public String toString() {
        return "task.Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", epicId=" + epicId +
                '}';
    }
}