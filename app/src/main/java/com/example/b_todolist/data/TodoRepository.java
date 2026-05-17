package com.example.b_todolist.data;

import com.example.b_todolist.model.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoRepository {
    private static final List<Todo> todos = new ArrayList<>();
    private static int nextId = 1;

    private TodoRepository() {
    }

    public static List<Todo> getAll() {
        return new ArrayList<>(todos);
    }

    public static Todo getById(int id) {
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

    public static void add(Todo todo) {
        if (todo.getId() <= 0) {
            todo.setId(nextId);
            nextId++;
        }
        todos.add(todo);
    }

    public static void update(Todo updatedTodo) {
        for (int i = 0; i < todos.size(); i++) {
            Todo currentTodo = todos.get(i);
            if (currentTodo.getId() == updatedTodo.getId()) {
                todos.set(i, updatedTodo);
                return;
            }
        }
    }

    public static void deleteById(int id) {
        for (int i = todos.size() - 1; i >= 0; i--) {
            if (todos.get(i).getId() == id) {
                todos.remove(i);
                return;
            }
        }
    }

    public static void sortByPriority() {
        Collections.sort(todos, new Comparator<Todo>() {
            @Override
            public int compare(Todo first, Todo second) {
                return getPriorityValue(first.getPriority()) - getPriorityValue(second.getPriority());
            }
        });
    }

    public static void sortByDate() {
        Collections.sort(todos, new Comparator<Todo>() {
            @Override
            public int compare(Todo first, Todo second) {
                return Long.compare(getDateValue(first), getDateValue(second));
            }
        });
    }

    // Feste Reihenfolge aus Teil 1: hohe Priorität steht in der Liste zuerst.
    private static int getPriorityValue(String priority) {
        if ("Hoch".equals(priority)) {
            return 1;
        }
        if ("Mittel".equals(priority)) {
            return 2;
        }
        if ("Niedrig".equals(priority)) {
            return 3;
        }
        return 4;
    }

    // Ein nicht gesetztes Datum wird beim Sortieren ans Ende geschoben.
    private static long getDateValue(Todo todo) {
        if (todo.getDueDateMillis() <= 0L) {
            return Long.MAX_VALUE;
        }
        return todo.getDueDateMillis();
    }
}
