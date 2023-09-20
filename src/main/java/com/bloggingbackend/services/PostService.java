package com.bloggingbackend.services;

import com.bloggingbackend.models.Post;
import com.bloggingbackend.payloads.PostDTO;
import com.bloggingbackend.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO,Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO,Integer postId);
    PostDTO getPostById(Integer postId);
    PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy,String dir);
    List<PostDTO> getAllPostsByUser(Integer userId);
    List<PostDTO> getAllPostsByCategory(Integer categoryId);
    List<PostDTO> searchPost(String keyword);
    void deletePost(Integer postId);
}
