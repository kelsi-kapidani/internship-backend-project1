package com.gisdev.library.dto.request;

public record UserUpdateDTO(

    String name,
    String surname,

    String username,
    String password,

    String email,

    Long libraryId
) {}
