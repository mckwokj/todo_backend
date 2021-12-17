package com.oocl.todo.controller;

import java.util.Arrays;
import java.util.List;

import com.oocl.todo.dto.TodoItemRequest;
import com.oocl.todo.entity.TodoItem;
import com.oocl.todo.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .andExpect((jsonPath("$[0].done").value(false)))
                .andExpect((jsonPath("$[1].id")).isString())
                .andExpect((jsonPath("$[1].text")).value("I am todo item 2"))
                .andExpect((jsonPath("$[1].done")).value(false));
    }

    @Test
    void should_insert_todo_item_when_perform_post_given_todo_item() throws Exception {
        // given
        String todoItem = "    {\n" +
                "        \"text\": \"I am todo item\"\n" +
                "    }";

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(TODO_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoItem))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect((jsonPath("$.text").value("I am todo item")))
                .andExpect((jsonPath("$.done")).value(false));
    }

    @Test
    void should_update_todo_item_text_when_perform_put_given_id_and_todo_item_request_and_todo_items() throws Exception {
        // given
        TodoItem todoItem = new TodoItem();

        todoItem.setDone(false);
        todoItem.setText("I am todo item");

        TodoItem todoItemInserted = todoRepository.insert(todoItem);

        String updatedTodoItem = "    {\n" +
                "        \"text\": \"I am todo item 3.\"\n" +
                "    }";


        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(TODO_ENDPOINT + "/{id}", todoItemInserted.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTodoItem))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.text").value("I am todo item 3."))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_update_todo_item_status_when_perform_put_given_id_and_todo_item_request_and_todo_items() throws Exception {
        // given
        TodoItem todoItem = new TodoItem();

        todoItem.setDone(false);
        todoItem.setText("I am todo item");

        TodoItem todoItemInserted = todoRepository.insert(todoItem);

        String updatedTodoItem = "    {\n" +
                "        \"done\": \"true\"\n" +
                "    }";


        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.put(TODO_ENDPOINT + "/{id}", todoItemInserted.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTodoItem))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.text").value("I am todo item"))
                .andExpect(jsonPath("$.done").value(true));
    }
    
    @Test
    void should_delete_todo_item_when_delete_todo_item_given_id() throws Exception {
        // given
        TodoItem todoItem = new TodoItem();

        todoItem.setDone(false);
        todoItem.setText("I am todo item");

        TodoItem todoItemInserted = todoRepository.insert(todoItem);

        int size = todoRepository.findAll().size();
        
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(TODO_ENDPOINT + "/{id}", todoItemInserted.getId()))
                .andExpect(status().isNoContent());

        assertEquals(--size, todoRepository.findAll().size());
    }
    




}
