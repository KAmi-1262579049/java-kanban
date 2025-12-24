package manager;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;

// Класс реализует интерфейс HistoryManager для хранения истории просмотров задач
public class InMemoryHistoryManager implements HistoryManager {
    // Класс Node представляет узел двусвязного списка
    private static class Node {
        Task task;  // Поле для хранения задачи
        Node prev;  // Ссылка на предыдущий узел в списке
        Node next;  // Ссылка на следующий узел в списке

        // Конструктор узла
        Node(Task task) {
            this.task = task;
        }
    }

    private final HashMap<Integer, Node> historyMap; // Хэш-таблица для быстрого доступа к узлам по id задачи
    private Node head; // Указатель на первый узел списка
    private Node tail; // Указатель на последний узел списка

    // Конструктор класса
    public InMemoryHistoryManager() {
        this.historyMap = new HashMap<>();
    }

    // Метод добавления задачи в историю
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        remove(task.getId());

        Node newNode = new Node(task);
        linkLast(newNode);
        historyMap.put(task.getId(), newNode);
    }

    // Метод удаления задачи из истории по id
    @Override
    public void remove(int id) {
        Node node = historyMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    // Метод получения истории просмотров
    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.task);
            current = current.next;
        }
        return result;
    }

    // Метод для добавления узла в конец списка
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

    // Метод для удаления узла из списка
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
