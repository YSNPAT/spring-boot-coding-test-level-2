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

    public User getUserById(UUID id) throws Exception {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent())
            return existingUser.get();
        else
            throw new Exception("User not found");

    }

    public User createUser(User user) throws Exception {
        Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (userByUsername.isPresent())
            throw new Exception("Username has been registered");
        else {
            userRepository.save(user);
            return user;
        }
    }

    public User updateUser(UUID id, User user) throws Exception {
        Optional<User> _user = userRepository.findById(id);
        if (_user.isPresent()) {
            Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
            if (userByUsername.isPresent())
                throw new Exception("Username has been registered");
            else {
                User existingUser = _user.get();
                existingUser.setUsername(user.getUsername());
                existingUser.setPassword(user.getPassword());
                userRepository.save(existingUser);
                return existingUser;
            }
        } else
            throw new Exception("User not found");
    }

    public User patchUser(UUID id, User user) throws Exception {
        boolean requiredUpdate = false;
        User existingUser = null;
        Optional<User> _user = userRepository.findById(id);
        if (_user.isPresent()) {
            existingUser = _user.get();
            if (StringUtils.hasLength(user.getUsername())) {
                Optional<User> userByUsername = userRepository.findUserByUsername(user.getUsername());
                if (userByUsername.isPresent())
                    throw new Exception("Username has been registered");
                else {
                    existingUser.setUsername(user.getUsername());
                    requiredUpdate = true;
                }
            }
            if (StringUtils.hasLength(user.getPassword())) {
                existingUser.setPassword(user.getPassword());
                requiredUpdate = true;
            }
        } else
            throw new Exception("User not found");
        if (requiredUpdate)
            userRepository.save(existingUser);
        return existingUser;
    }

    public void deleteUser(UUID id) throws Exception {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent())
            userRepository.deleteById(id);
        else
            throw new Exception("User not found");

    }
}