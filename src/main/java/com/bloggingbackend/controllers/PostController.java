package com.bloggingbackend.controllers;


import com.bloggingbackend.payloads.ApiResponse;
import com.bloggingbackend.payloads.PostDTO;
import com.bloggingbackend.payloads.PostResponse;
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

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/allPosts")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable("userId") Integer userId) {
        List<PostDTO> postDTOs = this.postService.getAllPostsByUser(userId);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("postId") Integer postId) {
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/allCategory")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<PostDTO> postDTOs = this.postService.getAllPostsByCategory(categoryId);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNo",defaultValue = "0",required = false) Integer pageNo,
                                                    @RequestParam(value="pageSize",defaultValue = "5",required = false) Integer pageSize,
                                                   @RequestParam(value="sortBy",defaultValue = "postId",required = false)String sortBy,
                                                   @RequestParam(value="dir",defaultValue = "asc",required = false)String dir
                                                   ) {

        PostResponse postDTOs = this.postService.getAllPosts(pageNo,pageSize,sortBy,dir);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }


    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchDTOs(@PathVariable("keywords") String keywords){
        List<PostDTO> postDTOs = this.postService.searchPost("%"+keywords+"%");
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }
}


