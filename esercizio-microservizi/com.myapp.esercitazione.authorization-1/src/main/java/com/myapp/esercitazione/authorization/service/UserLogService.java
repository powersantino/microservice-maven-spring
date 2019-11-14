package com.myapp.esercitazione.authorization.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myapp.esercitazione.authorization.model.UserLog;
import com.myapp.esercitazione.authorization.repository.UserLogRepository;

@Component
public class UserLogService {
    @Autowired
    private UserLogRepository userRepository;

    public UserLog save(UserLog user) {
        return userRepository.save(user);
    }

    public Optional<UserLog> getByToken(String token) {
        return userRepository.findByToken(token);
    }

    public Optional<UserLog> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
