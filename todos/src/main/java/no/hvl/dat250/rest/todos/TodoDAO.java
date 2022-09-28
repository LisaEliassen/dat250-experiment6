package no.hvl.dat250.rest.todos;

import java.util.Map;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class TodoDAO {
    private final Map<Long, Todo> todos = new HashMap<>();
    private final AtomicLong id_generator;

    public TodoDAO() {
        this.id_generator = new AtomicLong();
    }

    public Todo create(Todo todo) {
        if(todo.getId() == null) {
            Long ID = this.id_generator.incrementAndGet();
            todo.setId(ID);
        }
        this.add(todo);
        return todo;
    }
    public void add(Todo todo) {
        this.todos.put(todo.getId(), todo);
    }

    public Todo find(Long id) {
        return this.todos.get(id);
    }

    public Collection<Todo> all() {
        return todos.values();
    }

    public Todo update(Todo todo, Long id) {
        if (find(id) != null && todos.containsKey(id)) {
            Todo updateTodo = find(id);
            updateTodo.setDescription(todo.getDescription());
            updateTodo.setSummary(todo.getSummary());
            return updateTodo;
        }
        else {
            return null;
        }
    }
    public Todo remove(Long id) {
        Todo todo = todos.remove(id);
        return todo;
    }
}
