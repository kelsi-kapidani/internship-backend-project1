package com.gisdev.library.controller;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.CreateUserRequest;
import com.gisdev.library.service.LibraryUserService;
import org.springframework.web.bind.annotation.RequestBody;
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
public class LibraryUserController {

    public final LibraryUserService userService;

    @PostMapping("/add")
    public Object addUser(@Valid @RequestBody CreateUserRequest request) {

        if (userService.usernameExists(request.username())) {
            return new ResponseError("Username already exists");
        }

        return userService.createUser(request);
    }
}
