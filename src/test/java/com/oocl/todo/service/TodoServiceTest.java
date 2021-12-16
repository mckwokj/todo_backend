package com.oocl.todo.service;

import java.util.Arrays;
import java.util.List;

import com.oocl.todo.entity.TodoItem;
import com.oocl.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;
    @InjectMocks
    TodoService todoService;
    
    @Test
    void should_return_all_todo_items_when_find_all_todo_items_given_todo_items() {
        // given
        TodoItem todoItem = new TodoItem();
        todoItem.setText("I am todo item");
        todoItem.setDone(false);

        List<TodoItem> todoItems = Arrays.asList(todoItem);



        given(todoRepository.findAll())
                .willReturn(todoItems);

        // when
        List<TodoItem> actual = todoService.findAllTodoItems();

        // then
        assertEquals(todoItems, actual);
        assertEquals(todoItems.get(0).isDone(), actual.get(0).isDone());
        assertEquals(todoItems.get(0).getText(), actual.get(0).getText());
    }

    @Test
    void should_find_todo_item_when_find_by_id_given_todo_item() {
        // given
        TodoItem todoItem = new TodoItem();
        todoItem.setText("I am todo item");
        todoItem.setDone(false);
        todoItem.setId("123");

        given(todoRepository.findById(any()))
                .willReturn(java.util.Optional.of(todoItem));

        // when
        TodoItem actual = todoService.findTodoItemById(todoItem.getId());

        // then
        assertEquals(todoItem, actual);
        assertEquals(todoItem.isDone(), actual.isDone());
        assertEquals(todoItem.getText(), actual.getText());

    }

    @Test
    void should_insert_todo_item_when_insert_todo_item_given_todo_item() {
        // given
        TodoItem todoItem = new TodoItem();
        todoItem.setText("I am todo item");
        todoItem.setDone(false);
        todoItem.setId("123");

        given(todoRepository.insert(any(TodoItem.class)))
                .willReturn(todoItem);

        // when
        TodoItem actual = todoService.insertTodoItem(todoItem);

        // then
        assertEquals(todoItem, actual);
        assertEquals(todoItem.isDone(), actual.isDone());
        assertEquals(todoItem.getText(), actual.getText());
    }

    
}
