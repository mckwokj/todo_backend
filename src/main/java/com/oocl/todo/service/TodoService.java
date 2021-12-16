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


    public TodoItem findTodoItemById(String id) {
        return todoRepository.findById(id).orElseThrow(TodoItemNotFoundException::new);
    }
}
