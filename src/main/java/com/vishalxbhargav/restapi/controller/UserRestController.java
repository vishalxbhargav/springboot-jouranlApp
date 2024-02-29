package com.vishalxbhargav.restapi.controller;

import com.vishalxbhargav.restapi.entity.User;

import com.vishalxbhargav.restapi.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public List<User> getAllUser(){
        return userEntryService.getAll();
    }
    @GetMapping("id/{id}")
    public ResponseEntity<?> getuser(@PathVariable ObjectId id){
        Optional<User> user=userEntryService.findById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{username}")
    public ResponseEntity<?> update(@RequestBody User user,@PathVariable String username){
        User userInDb=userEntryService.findByUserName(username);
        if(userInDb!=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userEntryService.saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(userInDb,HttpStatus.NO_CONTENT);
    }
    @PostMapping
    public ResponseEntity<?> saveuser(@RequestBody User user){
        userEntryService.saveUser(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
