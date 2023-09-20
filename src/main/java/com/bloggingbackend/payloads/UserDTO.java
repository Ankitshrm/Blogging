package com.bloggingbackend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    @NotEmpty
    @Size(min = 3,message = "Username must be at least 3 characters long and at most 10 characters")
    private String name;
    @Email
    private String email;
    @NotEmpty
    @Size(min = 3,message = "Username must be at least 3 characters long and at most 10 characters")
    private String password;
    @NotEmpty
    private String about;
}
