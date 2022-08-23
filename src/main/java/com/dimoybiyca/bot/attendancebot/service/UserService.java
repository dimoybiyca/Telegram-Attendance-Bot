package com.dimoybiyca.bot.attendancebot.service;

import com.dimoybiyca.bot.attendancebot.model.User;
import com.dimoybiyca.bot.attendancebot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> readAll() {
        return userRepository.findAll();
    }

    public User readByVariant(int variant) {
        return userRepository.findByVariant(variant);
    }

    public User readByChatId(long id) {
        return userRepository.findByChatId(id);
    }

    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}