package com.vishalxbhargav.restapi.controller;

import com.vishalxbhargav.restapi.config.SecurityConfiguration;
import com.vishalxbhargav.restapi.entity.User;

import com.vishalxbhargav.restapi.repository.UserEntryRepo;
import com.vishalxbhargav.restapi.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserEntryRepo userEntryRepo;

//    @GetMapping("id/{id}")
//    public ResponseEntity<?> getuser(@PathVariable ObjectId id){
//        Optional<User> user=userEntryService.findById(id);
//        if(user.isPresent()){
//            return new ResponseEntity<>(user,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
//    }
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User userInDb=userEntryService.findByUserName(username);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userEntryService.saveNewUser(userInDb);
        return new ResponseEntity<>(userInDb,HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<?> update(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        userEntryRepo.deleteByUserName(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
