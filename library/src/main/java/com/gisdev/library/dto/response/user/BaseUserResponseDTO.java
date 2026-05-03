package com.gisdev.library.dto.response.user;

import com.gisdev.library.constants.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private Role role;
    private String email;
}
