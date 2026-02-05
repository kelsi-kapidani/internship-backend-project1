package com.gisdev.library.service;

import com.gisdev.library.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
