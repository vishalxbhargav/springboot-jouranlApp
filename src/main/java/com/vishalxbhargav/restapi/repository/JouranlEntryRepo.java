package com.vishalxbhargav.restapi.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;

import com.vishalxbhargav.restapi.entity.JournalEntry;

public interface JouranlEntryRepo extends MongoRepository<JournalEntry,ObjectId>{
    
}