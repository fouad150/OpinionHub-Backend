package com.youcode.opinionhub.Service.impl;

import com.youcode.opinionhub.Entity.User;
import com.youcode.opinionhub.Repository.UserRepository;
import com.youcode.opinionhub.Service.UserService;
import com.youcode.opinionhub.exception.DoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByEmail(String email){

        Optional<User>  userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new DoesNotExistException("this user doesn't exists");
        }

        return userOptional.get();
    }
}
