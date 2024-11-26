package com.task.taskcard.service.impl;

import com.task.taskcard.model.User;
import com.task.taskcard.repository.UserRepository;
import com.task.taskcard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public String getUsernameById(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getUsername();
    }
}

