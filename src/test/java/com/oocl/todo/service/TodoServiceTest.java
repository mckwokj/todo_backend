package com.oocl.todo.service;

import java.util.Arrays;
import java.util.List;

import com.oocl.todo.entity.TodoItem;
import com.oocl.todo.exception.TodoItemNotFoundException;
import com.oocl.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

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
    void should_throw_todo_item_not_found_exception_when_find_by_id_given_id() {
        // given
        String id = "123";

        String exceptionMsg = "Todo item not found";

        given(todoRepository.findById(id))
                .willThrow(new TodoItemNotFoundException());

        // when
        // then
        TodoItemNotFoundException todoItemNotFoundException = assertThrows(TodoItemNotFoundException.class, () -> {
                todoService.findTodoItemById(id);
        });

        assertEquals(exceptionMsg, todoItemNotFoundException.getMessage());
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

    @Test
    void should_update_todo_item_text_when_update_todo_item_given_id_and_updated_todo_item_and_old_todo_item() {
        // given
        String id = "123";

        TodoItem updatedTodoItem = new TodoItem();
        updatedTodoItem.setText("I am new todo item");

        TodoItem todoItem = new TodoItem();
        todoItem.setText("I am todo item");
        todoItem.setDone(false);
        todoItem.setId("123");

        given(todoRepository.findById(any()))
                .willReturn(java.util.Optional.of(todoItem));

        todoItem.setText("I am new todo item");

        given(todoRepository.save(any()))
                .willReturn(todoItem);

        // when
        TodoItem actual = todoService.updateTodoItem(id, updatedTodoItem);

        // then
        assertEquals(todoItem, actual);
        assertEquals(todoItem.isDone(), actual.isDone());
        assertEquals(todoItem.getText(), actual.getText());
        assertEquals(todoItem.getId(), actual.getId());
    }

    @Test
    void should_update_todo_item_done_status_when_update_todo_item_given_id_and_updated_todo_item_and_old_todo_item() {
        // given
        String id = "123";

        TodoItem updatedTodoItem = new TodoItem();
        updatedTodoItem.setDone(true);

        TodoItem todoItem = new TodoItem();
        todoItem.setText("I am todo item");
        todoItem.setDone(false);
        todoItem.setId("123");

        given(todoRepository.findById(any()))
                .willReturn(java.util.Optional.of(todoItem));

        todoItem.setDone(true);

        given(todoRepository.save(any()))
                .willReturn(todoItem);

        // when
        TodoItem actual = todoService.updateTodoItem(id, updatedTodoItem);

        // then
        assertEquals(todoItem, actual);
        assertEquals(todoItem.isDone(), actual.isDone());
        assertEquals(todoItem.getText(), actual.getText());
        assertEquals(todoItem.getId(), actual.getId());
    }

    @Test
    void should_delete_todo_item_when_delete_todo_item_given_id_and_todo_item() {
        // given
        TodoItem todoItem = new TodoItem();
        todoItem.setText("I am todo item");
        todoItem.setDone(false);
        todoItem.setId("123");

        given(todoRepository.findById(any()))
                .willReturn(java.util.Optional.of(todoItem));

        willDoNothing().given(todoRepository).deleteById(any());

        // when
        todoService.deleteTodoItem(todoItem.getId());

        // then
        verify(todoRepository).deleteById(todoItem.getId());
    }
}
