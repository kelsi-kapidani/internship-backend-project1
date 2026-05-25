package com.gisdev.library.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordRequestDTO {

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*",
            message = "Password must contain at least one special character"
    )
    private String newPassword;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = ".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*",
            message = "Password must contain at least one special character"
    )
    private String repeatedPassword;

}
