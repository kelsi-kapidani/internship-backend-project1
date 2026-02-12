package com.gisdev.library.controller;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.CreateUserRequest;
import com.gisdev.library.service.LibraryUserService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

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

    @GetMapping("/{id}")
    public Object getUser(@PathVariable long id) {

        if (!userService.idExists(id)) {
            return new ResponseError("User with this id does not exist");
        }
        return userService.getUserById(id);
    }
}
