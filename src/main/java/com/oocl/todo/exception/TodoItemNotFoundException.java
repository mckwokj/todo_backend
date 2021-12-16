package com.oocl.todo.exception;

public class TodoItemNotFoundException extends RuntimeException{
    public TodoItemNotFoundException() {
        super("Todo item not found");
    }
}
