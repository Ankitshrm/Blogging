package com.bloggingbackend.services.impl;

import com.bloggingbackend.exceptions.ResourceNotFoundException;
import com.bloggingbackend.models.User;
import com.bloggingbackend.payloads.UserDTO;
import com.bloggingbackend.repositories.UserRepository;
import com.bloggingbackend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.modelMapper.map(userDTO, User.class);
        User savedUser = this.userRepository.save(user);
        return this.modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {
        User user = this.modelMapper.map(userDTO, User.class);
        User findUser =this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
        findUser.setName(user.getName());
        findUser.setAbout(user.getAbout());
        findUser.setEmail(user.getEmail());
        findUser.setPassword(user.getPassword());
        return this.modelMapper.map(findUser,UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> allUsers = this.userRepository.findAll();
        return allUsers.stream().map(it ->  this.modelMapper.map(it,UserDTO.class)).toList();
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user =this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
        return this.modelMapper.map(user,UserDTO.class);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user =this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
        this.userRepository.delete(user);
    }

}
