package com.bloggingbackend.services;

import com.bloggingbackend.models.Post;
import com.bloggingbackend.payloads.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO,Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO,Integer postId);
    PostDTO getPostById(Integer postId);
    List<PostDTO> getAllPosts();
    List<PostDTO> getAllPostsByUser(Integer userId);
    List<PostDTO> getAllPostsByCategory(Integer categoryId);
    List<PostDTO> searchPost(String keyword);
    void deletePost(Integer postId);
}
