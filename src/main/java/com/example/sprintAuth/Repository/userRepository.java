package com.example.sprintAuth.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sprintAuth.User.User;
import java.util.Optional;


public interface userRepository  extends MongoRepository<User, Integer> {
    
    Optional<User> findByEmail(String email);
}
