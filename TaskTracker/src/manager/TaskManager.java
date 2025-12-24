package manager;

import task.Task;
import task.TaskStatus;
import task.Epic;
import task.Subtask;
import java.util.ArrayList;
import java.util.HashMap;

// Основной класс для управления задачами, эпиками и подзадачами
public class TaskManager {
    private final HashMap<Integer, Task> tasks; // HashMap для хранения обычных задач
    private final HashMap<Integer, Epic> epics; // HashMap для хранения эпиков
    private final HashMap<Integer, Subtask> subtasks; // HashMap для хранения подзадач
    private final HistoryManager historyManager; // Менеджер истории для отслеживания просмотренных задач
    private int nextId; // Счетчик для генерации уникальных id

    // Конструктор класса
    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.historyManager = new HistoryManager();
        this.nextId = 1;
    }

    // Приватный метод для генерации нового уникального id
    private int generateId() {
        return nextId++;
    }

    // Метод для получения списка всех обычных задач
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Метод для удаления всех обычных задач
    public void deleteAllTasks() {
        tasks.values().forEach(task -> historyManager.remove(task.getId()));
        tasks.clear();
    }

    // Метод для получения задачи по id
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    // Метод для создания новой обычной задачи
    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Задача не может быть null");
        }
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    // Метод для обновления существующей задачи
    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    // Метод для удаления задачи по id
    public void deleteTaskById(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    // Метод для получения списка всех эпиков
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    // Метод для удаления всех эпиков
    public void deleteAllEpics() {
        subtasks.values().forEach(subtask -> historyManager.remove(subtask.getId()));
        subtasks.clear();

        epics.values().forEach(epic -> historyManager.remove(epic.getId()));
        epics.clear();
    }

    // Метод для получения эпика по id
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    // Метод для создания нового эпика
    public Epic createEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Эпик не может быть null");
        }
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    // Метод для обновления существующего эпика
    public void updateEpic(Epic epic) {
        if (epic == null || !epics.containsKey(epic.getId())) {
            return;
        }
        Epic savedEpic = epics.get(epic.getId());
        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

    // Метод для удаления эпика по id
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return;
        }

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }

        epics.remove(id);
        historyManager.remove(id);
    }

    // Метод для получения списка всех подзадач
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Метод для удаления всех подзадач
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            updateEpicStatus(epic.getId());
        }

        subtasks.values().forEach(subtask -> historyManager.remove(subtask.getId()));
        subtasks.clear();
    }

    // Метод для получения подзадачи по id
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    // Метод для создания новой подзадачи
    public Subtask createSubtask(Subtask subtask) {
        if (subtask == null) {
            throw new IllegalArgumentException("Подзадача не может быть null");
        }

        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            throw new IllegalArgumentException("Эпик с id=" + subtask.getEpicId() + " не найден");
        }

        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtaskId(subtask.getId());
        updateEpicStatus(epic.getId());
        return subtask;
    }

    // Метод для обновления существующей подзадачи
    public void updateSubtask(Subtask subtask) {
        if (subtask == null || !subtasks.containsKey(subtask.getId())) {
            return;
        }

        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            throw new IllegalArgumentException("Эпик с id=" + epicId + " не найден");
        }

        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epicId);
    }

    // Метод для удаления подзадачи по id
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            return;
        }

        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.removeSubtaskId(id);
            updateEpicStatus(epic.getId());
        }

        subtasks.remove(id);
        historyManager.remove(id);
    }

    // Метод для получения списка всех подзадач определенного эпика
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return new ArrayList<>();
        }

        ArrayList<Subtask> result = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                result.add(subtask);
            }
        }
        return result;
    }

    // Метод для получения истории просмотров задач
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    // Приватный метод для обновления статуса эпика
    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        // Флаги для определения статуса эпика
        boolean allNew = true; // Все подзадачи имеют статус NEW
        boolean allDone = true; // Все подзадачи имеют статус DONE

        // Проверка статусов всех подзадач
        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask == null) {
                continue;
            }

            TaskStatus status = subtask.getStatus();
            if (status != TaskStatus.NEW) {
                allNew = false;
            }
            if (status != TaskStatus.DONE) {
                allDone = false;
            }
        }

        // Установка статуса эпика в зависимости от статусов подзадач
        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}