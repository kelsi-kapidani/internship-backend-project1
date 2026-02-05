package com.gisdev.library.controller;

import com.gisdev.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
}
