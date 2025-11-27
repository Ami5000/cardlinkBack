package com.api.cardlink.Dto.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Le prénom ne peut pas etre vide")
    private String firstName;
    @NotBlank(message = "Le nom ne peut pas etre vide")
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String password;

    private String avatarUrl;

}
