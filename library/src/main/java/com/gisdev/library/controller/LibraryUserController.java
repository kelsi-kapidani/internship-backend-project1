package com.gisdev.library.controller;

import com.gisdev.library.dto.ResponseError;
import com.gisdev.library.dto.request.UserCreateDTO;
import com.gisdev.library.dto.request.UserUpdateDTO;
import com.gisdev.library.entity.LibraryUser;
import com.gisdev.library.service.LibraryUserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RestController
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
@Validated
public class LibraryUserController {

    public final LibraryUserService userService;

    @PostMapping("/create")
    public Object createUser(@Valid @RequestBody UserCreateDTO request) {

        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public Object getUser(@PathVariable long id) {

        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public Object updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO request) {

        return userService.updateUser(id,request);
    }

    @PatchMapping("/activate/{id}")
    public Object activateUser(@PathVariable Long id) {

        return userService.setUserActive(id);
    }

    @PatchMapping("/password/{id}")
    public Object changePassword(
            @PathVariable Long id,
            @NotBlank(message = "Password is empty")
            @Pattern(
                    regexp = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*",
                    message = "Password must contain at least one special character"
            )
            String password) {

        return userService.changePassword(id, password);
    }
}
