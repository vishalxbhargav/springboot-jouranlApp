package com.vishalxbhargav.restapi.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.vishalxbhargav.restapi.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vishalxbhargav.restapi.entity.JournalEntry;
import com.vishalxbhargav.restapi.repository.JouranlEntryRepo;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JournalEntryService {
    @Autowired
    private JouranlEntryRepo jouranlEntryRepo;
    @Autowired
    private UserEntryService userEntryService;
    @Transactional
    public void createEntry(JournalEntry jouranalEntry, String username){
       try{
           User user=userEntryService.findByUserName(username);
           jouranalEntry.setDate(LocalDateTime.now());
           JournalEntry saved = jouranlEntryRepo.save(jouranalEntry);
           user.getJournalEntries().add(saved);
           userEntryService.saveUser(user);
       }catch (Exception e){
           System.out.println(e);
           throw new RuntimeException("An Error occur during saving entry...",e);
       }
    }
    public void createEntry(JournalEntry jouranalEntry){
        jouranlEntryRepo.save(jouranalEntry);
    }
    public List<JournalEntry> getAll(){
        return jouranlEntryRepo.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id) {
        return jouranlEntryRepo.findById(id);
    }
    public void deleteById(ObjectId id, String username) {
        User user=userEntryService.findByUserName(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userEntryService.saveUser(user);
        jouranlEntryRepo.deleteById(id);
    }
}
