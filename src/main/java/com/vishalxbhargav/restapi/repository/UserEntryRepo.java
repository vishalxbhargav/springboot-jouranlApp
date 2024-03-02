package com.vishalxbhargav.restapi.repository;

import com.vishalxbhargav.restapi.entity.JournalEntry;
import com.vishalxbhargav.restapi.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepo extends MongoRepository<User,ObjectId>{
    User findByUserName(String username);
    void deleteByUserName(String username);
}