package com.vishalxbhargav.restapi.service;

import com.vishalxbhargav.restapi.entity.User;
import com.vishalxbhargav.restapi.repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepo userEntryRepo;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public User findByUserName(String username){
        return userEntryRepo.findByUserName(username);
    }
    public void saveUser(User user) {
        userEntryRepo.save(user);
    }

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        userEntryRepo.save(user);
    }

    public List<User> getAll() {
        return userEntryRepo.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id) {
        userEntryRepo.deleteById(id);
    }
}
