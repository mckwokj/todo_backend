package com.oocl.todo.controller;

import java.util.Arrays;
import java.util.List;

import com.oocl.todo.entity.TodoItem;
import com.oocl.todo.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    private static final String TODO_ENDPOINT = "/todos";

    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    TodoRepository todoRepository;

    @AfterEach
    void cleanRepository(){
        todoRepository.deleteAll();
    }
    
    @Test
    void should_find_all_todo_items_when_perform_get_given_todo_items() throws Exception {
        // given
        TodoItem todoItem = new TodoItem();
        TodoItem todoItem2 = new TodoItem();

        todoItem.setDone(false);
        todoItem.setText("I am todo item");
        todoItem2.setDone(false);
        todoItem2.setText("I am todo item 2");

        todoRepository.insert(todoItem);
        todoRepository.insert(todoItem2);

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get(TODO_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect((jsonPath("$[0].text").value("I am todo item")))
                .andExpect((jsonPath("$[1].id")).isString())
                .andExpect((jsonPath("$[1].text")).value("I am todo item 2"));
    }
    

}
