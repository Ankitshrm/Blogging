package com.bloggingbackend.services;

import com.bloggingbackend.payloads.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user,Integer userId);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Integer userId);

    void deleteUser(Integer userId);

}
