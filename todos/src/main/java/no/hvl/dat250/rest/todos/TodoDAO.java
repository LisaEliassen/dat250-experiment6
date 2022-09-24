package no.hvl.dat250.rest.todos;

import java.util.Map;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.*;

public class TodoDAO {
    private final Map<Long, Todo> todos = new HashMap<>();
    private final AtomicLong id_generator;

    public TodoDAO() {
        this.id_generator = new AtomicLong();
    }
    public void create(String summary, String description) {
        Long id = this.id_generator.incrementAndGet();
        Todo todo = new Todo(summary, description);
        this.todos.put(id, todo);
    }

    public void add(Todo todo) {
        this.todos.put(todo.getId(), todo);
    }

    public Todo find(Long id) {
        return this.todos.get(id);
    }

    public Todo update(Long id, String summary, String description) {
        if (find(id) != null) {
            if (summary != null) {
                find(id).setSummary(summary);
            }
            if (description != null) {
                find(id).setDescription((description));
            }
        }
        return find(id);
    }

    public Todo remove(String id) {
        Todo todo = this.todos.remove(id);

        return todo;
    }

    public Map<Long,Todo> all() {
        return this.todos;
    }

}
