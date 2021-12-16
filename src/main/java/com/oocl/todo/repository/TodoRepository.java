package com.oocl.todo.repository;

import com.oocl.todo.entity.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoItem, String> {
}
