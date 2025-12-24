package manager;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;

// Класс HistoryManager управляет историей просмотров задач
public class HistoryManager {
    // Внутренний статический класс Node для реализации двусвязного списка
    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
        }
    }

    // HashMap для быстрого доступа к узлам по id задачи
    private final HashMap<Integer, Node> historyMap;
    private Node head;
    private Node tail;

    // Конструктор класса
    public HistoryManager() {
        this.historyMap = new HashMap<>();
    }

    // Метод для добавления задачи в историю
    public void add(Task task) {
        if (task == null) {
            return;
        }

        remove(task.getId());

        Node newNode = new Node(task);
        linkLast(newNode);
        historyMap.put(task.getId(), newNode);
    }

    // Метод для удаления задачи из истории по id
    public void remove(int id) {
        Node node = historyMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    // Метод для получения списка всех задач из истории
    public ArrayList<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.task);
            current = current.next;
        }
        return result;
    }

    // Приватный метод для добавления узла в конец списка
    private void linkLast(Node node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    // Приватный метод для удаления узла из списка
    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }
}