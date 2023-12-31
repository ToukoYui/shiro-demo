package com.example.service;


import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getByUsername(String username) {
        return userMapper.queryUserByName(username);
    }

    public List<User> getAllUsers() {
        return userMapper.queryUserList();
    }



}
