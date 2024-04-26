package com.example.clearsolutiontestapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    public String email;

    @NotNull(message = "First name cannot be null")
    public String firstName;

    @NotNull(message = "Last name cannot be null")
    public String lastName;

    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    public LocalDate birthDate;

    public String address;

    public String phoneNumber;

}
