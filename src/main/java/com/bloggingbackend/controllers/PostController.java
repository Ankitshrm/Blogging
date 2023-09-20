package com.bloggingbackend.controllers;


import com.bloggingbackend.payloads.PostDTO;
import com.bloggingbackend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO post, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
        PostDTO postDTO = this.postService.createPost(post, userId, categoryId);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO post, @PathVariable("postId") Integer postId) {
        PostDTO updateDTO = this.postService.updatePost(post, postId);
        return new ResponseEntity<>(updateDTO, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") Integer postId) {
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPost() {
        List<PostDTO> postDTOs = this.postService.getAllPosts();
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }
}


