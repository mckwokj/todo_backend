package com.oocl.todo.mapper;

import com.oocl.todo.dto.TodoItemRequest;
import com.oocl.todo.entity.TodoItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {
    public TodoItem toEntity(TodoItemRequest todoItemRequest) {
        TodoItem todoItem = new TodoItem();
        BeanUtils.copyProperties(todoItemRequest, todoItem);

        return todoItem;
    }
}
