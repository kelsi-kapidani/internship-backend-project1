package com.gisdev.library.controller;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.CreateUserRequest;
import com.gisdev.library.dto.request.UpdateUserRequest;
import com.gisdev.library.entity.LibraryUser;
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

    @PostMapping("/create")
    public Object createUser(@Valid @RequestBody CreateUserRequest request) {

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
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public Object updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {

        LibraryUser user = userService.updateUser(id, request);
        if (user == null) {
            return new ResponseError("User you are trying to update does not exist");
        }
        return user;
    }

    @PutMapping("/activate/{id}")
    public ResponseError activateUser(@PathVariable Long id) {
        if (userService.setUserActive(id)) {
            return new ResponseError("User activated successfully");
        }
        return new ResponseError("User you tried to activate not found");

    }

    @PutMapping("/password/{id}")
    public Object changePassword(@PathVariable Long id, @Valid @RequestBody String password) {

        boolean result = userService.changePassword(id, password);
        if (result) {
            return new ResponseError("Password changed successfully");
        }
        return new ResponseError("New password is the same as the old one");
    }
}
