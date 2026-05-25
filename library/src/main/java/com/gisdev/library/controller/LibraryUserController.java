package com.gisdev.library.controller;

import com.gisdev.library.dto.request.user.BaseUserRequestDTO;
import com.gisdev.library.dto.request.user.ChangeUserRequestDTO;
import com.gisdev.library.dto.request.user.NewPasswordRequestDTO;
import com.gisdev.library.dto.response.user.FullUserResponseDTO;
import com.gisdev.library.service.iservice.ILibraryUserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final ILibraryUserService userService;

    @PostMapping("/create")
    public ResponseEntity<Long> createUser(@Valid @RequestBody BaseUserRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("/{id}")
    public FullUserResponseDTO getUser(@PathVariable long id) {

        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUser(@PathVariable Long id, @Valid @RequestBody ChangeUserRequestDTO request) {

        return ResponseEntity.ok(userService.updateUser(id,request));
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Long> activateUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.setUserActive(id));
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<Long> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody NewPasswordRequestDTO request) {

        return ResponseEntity.ok(userService.changePassword(id, request));
    }
}
