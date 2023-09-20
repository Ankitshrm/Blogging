package com.bloggingbackend.services.impl;

import com.bloggingbackend.exceptions.ResourceNotFoundException;
import com.bloggingbackend.models.Category;
import com.bloggingbackend.models.Post;
import com.bloggingbackend.models.User;
import com.bloggingbackend.payloads.PostDTO;
import com.bloggingbackend.repositories.CategoryRepository;
import com.bloggingbackend.repositories.PostRepository;
import com.bloggingbackend.repositories.UserRepository;
import com.bloggingbackend.services.CategoryService;
import com.bloggingbackend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;




    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->  new ResourceNotFoundException("Category","CategoryId",categoryId));

        Post post = this.modelMapper.map(postDTO,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        return this.modelMapper.map(savedPost,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = this.modelMapper.map(postDTO,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());

        return null;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return null;
    }

    @Override
    public List<PostDTO> getAllPostsByUser(Integer userId) {
        return null;
    }

    @Override
    public List<PostDTO> getAllPostsByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<PostDTO> searchPost(String keyword) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }
}
