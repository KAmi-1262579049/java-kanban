package managers.impl;

import managers.HistoryManager;
import managers.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;
import enums.TaskStatus;

import java.util.*;

// Класс для реализации менеджера задач в памяти
public class InMemoryTaskManager implements TaskManager {

    private Integer nextId = 1; // Счетчик для генерации уникальных id
    private final HashMap<Integer, Task> tasks = new HashMap<>(); // Хранилище обычных задач
    private final HashMap<Integer, Epic> epics = new HashMap<>(); // Хранилище эпиков
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>(); // Хранилище подзадач
    private final HistoryManager historyManager; // Менеджер истории просмотров

    // Конструктор класса
    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    // Метод для получения всех обычных задач
    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    // Метод для получения задачи по id
    @Override
    public Task getTask(Integer id) {
        Task task = tasks.get(id);

        if (task != null) {
            historyManager.add(task);
        }

        return task;
    }

    // Метод для добавления новой задачи
    @Override
    public Integer addNewTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);

        return task.getId();
    }

    // Метод для обновления задачи
    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    // Метод для удаления задачи по id
    @Override
    public void deleteTask(Integer id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    // Метод для удаления всех обычных задач
    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.remove(id);
        }

        tasks.clear();
    }

    // Метод для получения всех эпиков
    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    // Метод для получения эпика по id
    @Override
    public Epic getEpic(Integer id) {
        Epic epic = epics.get(id);

        if (epic != null) {
            historyManager.add(epic);
        }

        return epic;
    }

    // Метод для добавления нового эпика
    @Override
    public Integer addNewEpic(Epic epic) {
        epic.setId(nextId++);

        epics.put(epic.getId(), epic);

        return epic.getId();
    }

    // Метод для обновления эпика
    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic savedEpic = epics.get(epic.getId());

            savedEpic.setName(epic.getName());
            savedEpic.setDescription(epic.getDescription());
        }
    }

    // Метод для удаления эпика по id
    @Override
    public void deleteEpic(Integer id) {
        Epic epic = epics.remove(id);

        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }

            historyManager.remove(id);
        }
    }

    // Метод для удаления всех эпиков
    @Override
    public void deleteAllEpics() {
        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }

        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }

        epics.clear();
        subtasks.clear();
    }

    // Метод для получения всех подзадач
    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Метод для получения подзадачи по id
    @Override
    public Subtask getSubtask(Integer id) {
        Subtask subtask = subtasks.get(id);

        if (subtask != null) {
            historyManager.add(subtask);
        }

        return subtask;
    }

    // Метод для добавления новой подзадачи
    @Override
    public Integer addNewSubtask(Subtask subtask) {
        subtask.setId(nextId++);

        subtasks.put(subtask.getId(), subtask);

        Epic epic = epics.get(subtask.getEpicId());

        if (epic != null) {
            epic.addSubtaskId(subtask.getId());
            updateEpicStatus(epic.getId());
        }

        return subtask.getId();
    }

    // Метод для обновления подзадачи
    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateEpicStatus(subtask.getEpicId());
        }
    }

    // Метод для удаления подзадачи по id
    @Override
    public void deleteSubtask(Integer id) {
        Subtask subtask = subtasks.remove(id);

        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());

            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic.getId());
            }
            historyManager.remove(id);
        }
    }

    // Метод для удаления всех подзадач
    @Override
    public void deleteAllSubtasks() {
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }

        subtasks.clear();

        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            updateEpicStatus(epic.getId());
        }
    }

    // Метод для получения всех подзадач определенного эпика
    @Override
    public ArrayList<Subtask> getEpicSubtasks(Integer epicId) {
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return new ArrayList<>();
        }

        ArrayList<Subtask> epicSubtasks = new ArrayList<>();

        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);

            if (subtask != null) {
                epicSubtasks.add(subtask);
            }
        }

        return epicSubtasks;
    }

    // Метод для получения истории просмотров
    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    // Вспомогательный метод для обновления статуса эпика
    private void updateEpicStatus(Integer epicId) {
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return;
        }

        ArrayList<Subtask> epicSubtasks = getEpicSubtasks(epicId);

        if (epicSubtasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;    // Все подзадачи NEW
        boolean allDone = true;   // Все подзадачи DONE

        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }

            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }
}