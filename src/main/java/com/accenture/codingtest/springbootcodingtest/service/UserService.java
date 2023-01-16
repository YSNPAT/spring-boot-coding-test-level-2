package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.entity.User;
import com.accenture.codingtest.springbootcodingtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    public void createUser(User user) throws Exception {
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername.isPresent())
            throw new Exception("Username has been registered");
        userRepository.save(user);
    }

    public void updateUser(UUID id, User user) throws Exception {
        User _user = userRepository.findById(id).orElseThrow();
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername.isPresent())
            throw new Exception("Username has been registered");
        _user.setUsername(user.getUsername());
        _user.setPassword(user.getPassword());
        userRepository.save(_user);
    }

    public void patchUser(UUID id, User user) throws Exception {
        User _user = userRepository.findById(id).orElseThrow();
        boolean requiredUpdate = false;
        if (StringUtils.hasLength(user.getUsername())) {
            Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
            if (userByUsername.isPresent())
                throw new Exception("Username has been registered");
            _user.setUsername(user.getUsername());
            requiredUpdate = true;
        }
        if (StringUtils.hasLength(user.getPassword())) {
            _user.setPassword(user.getPassword());
            requiredUpdate = true;
        }
        if (requiredUpdate)
            userRepository.save(_user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}