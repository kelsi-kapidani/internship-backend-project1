package com.gisdev.library.controller;

import com.gisdev.library.dto.request.user.UserCUDTO;
import com.gisdev.library.service.LibraryUserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
@Validated
public class LibraryUserController {

    public final LibraryUserService userService;

    @PostMapping("/create")
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserCUDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public Object getUser(@PathVariable long id) {

        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUser(@PathVariable Long id, @Valid @RequestBody UserCUDTO request) {

        return ResponseEntity.ok(userService.updateUser(id,request));
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Long> activateUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.setUserActive(id));
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<Long> changePassword(
            @PathVariable Long id,
            @NotBlank(message = "Password is empty")
            @Pattern(
                    regexp = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*",
                    message = "Password must contain at least one special character"
            )
            String password) {

        return ResponseEntity.ok(userService.changePassword(id, password));
    }
}
