package com.oocl.todo.service;

import java.util.List;

import com.oocl.todo.entity.TodoItem;
import com.oocl.todo.exception.TodoItemNotFoundException;
import com.oocl.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoItem> findAllTodoItems() {
        return todoRepository.findAll();
    }

    public TodoItem findTodoItemById(String id) {
        return todoRepository.findById(id).orElseThrow(TodoItemNotFoundException::new);
    }

    public TodoItem insertTodoItem(TodoItem todoItem) {
        todoItem.setDone(false);
        return todoRepository.insert(todoItem);
    }

    public TodoItem updateTodoItem(String id, TodoItem updatedTodoItem) {
        TodoItem todoItem = findTodoItemById(id);

        if (updatedTodoItem.getText() != null) {
            todoItem.setText(updatedTodoItem.getText());
        }

        if (updatedTodoItem.isDone() != null) {
            todoItem.setDone(updatedTodoItem.isDone());
        }

        return todoRepository.save(todoItem);
    }
}
