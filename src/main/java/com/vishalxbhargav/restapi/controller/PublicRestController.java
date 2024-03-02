package com.vishalxbhargav.restapi.controller;

import com.vishalxbhargav.restapi.entity.User;
import com.vishalxbhargav.restapi.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicRestController {
    @Autowired
    private UserEntryService userEntryService;
    @PostMapping
    @RequestMapping("/create-user")
    public ResponseEntity<?> saveNewUser(@RequestBody User user){
        userEntryService.saveNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
