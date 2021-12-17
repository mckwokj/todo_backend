package com.oocl.todo.controller;

import java.util.List;

import com.oocl.todo.dto.TodoItemRequest;
import com.oocl.todo.entity.TodoItem;
import com.oocl.todo.mapper.TodoMapper;
import com.oocl.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("todos")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {
    private TodoService todoService;
    private TodoMapper todoMapper;

    public TodoController(TodoService todoService, TodoMapper todoMapper) {
        this.todoService = todoService;
        this.todoMapper = todoMapper;
    }

    @GetMapping
    public List<TodoItem> findAllTodoItems() {
        return todoService.findAllTodoItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem insertTodoItem(@RequestBody TodoItem todoItem) {
        return todoService.insertTodoItem(todoItem);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoItem updateTodoItem(@PathVariable String id, @RequestBody TodoItemRequest todoItemRequest) {
        return todoService.updateTodoItem(id, todoMapper.toEntity(todoItemRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable String id) {
        todoService.deleteTodoItem(id);
    }
}
