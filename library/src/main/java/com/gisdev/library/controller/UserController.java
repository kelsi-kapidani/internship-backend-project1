package com.gisdev.library.controller;

import com.gisdev.library.dto.request.CreateUserRequest;
import com.gisdev.library.entity.User;
import com.gisdev.library.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping("/add")
    public User addUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}
