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
    
}
