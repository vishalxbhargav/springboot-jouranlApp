package com.vishalxbhargav.restapi.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.vishalxbhargav.restapi.entity.User;
import com.vishalxbhargav.restapi.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vishalxbhargav.restapi.entity.JournalEntry;
import com.vishalxbhargav.restapi.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalRestController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;
    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> saveEntry(@RequestBody JournalEntry journalEntry,@PathVariable String username){
        try {
            journalEntryService.createEntry(journalEntry,username);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("{username}")
    public ResponseEntity<?> getAll(@PathVariable String username){
        User user =userEntryService.findByUserName(username);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null&&!all.isEmpty()) return new ResponseEntity<>(all,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("id/{myid}")
    public ResponseEntity<?> getJouranlById(@PathVariable ObjectId myid){
        Optional<JournalEntry> entry = journalEntryService.findById(myid);
        return entry.map(journalEntry -> new ResponseEntity<>(journalEntry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("id/{username}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @PathVariable String username,
            @RequestBody JournalEntry newEntry){

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if (journalEntry.isPresent()) {
            JournalEntry old = journalEntry.get();
            old.setTitle(!newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.createEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{username}/{myid}")
    public ResponseEntity<?> deleteJouranl(@PathVariable ObjectId myid,@PathVariable String username){
        Optional<JournalEntry> entry = journalEntryService.findById(myid);
        if(entry.isPresent()){
            journalEntryService.deleteById(myid,username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
