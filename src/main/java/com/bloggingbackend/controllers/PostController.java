package com.bloggingbackend.controllers;


import com.bloggingbackend.config.ConstantsValue;
import com.bloggingbackend.payloads.ApiResponse;
import com.bloggingbackend.payloads.PostDTO;
import com.bloggingbackend.payloads.PostResponse;
import com.bloggingbackend.services.FileService;
import com.bloggingbackend.services.PostService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value="pageNo",defaultValue = ConstantsValue.PAGE_NUMBER,required = false) Integer pageNo,
                                                    @RequestParam(value="pageSize",defaultValue = ConstantsValue.PAGE_SIZE,required = false) Integer pageSize,
                                                   @RequestParam(value="sortBy",defaultValue = ConstantsValue.SORT_BY,required = false)String sortBy,
                                                   @RequestParam(value="dir",defaultValue = ConstantsValue.SORT_DIR,required = false)String dir
                                                   ) {

        PostResponse postDTOs = this.postService.getAllPosts(pageNo,pageSize,sortBy,dir);
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }


    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchDTOs(@PathVariable("keywords") String keywords){
        List<PostDTO> postDTOs = this.postService.searchPost("%"+keywords+"%");
        return new ResponseEntity<>(postDTOs, HttpStatus.OK);
    }

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image")MultipartFile file,
            @PathVariable("postId") Integer postId
            ) throws IOException {
        PostDTO postDTO =this.postService.getPostById(postId);

        String fileName =this.fileService.uploadImage(path,file);
        postDTO.setImageName(fileName);
        PostDTO updatedpostDTO =this.postService.updatePost(postDTO,postId);
        return new ResponseEntity<>(updatedpostDTO,HttpStatus.OK);
    }


    @GetMapping(value = "/image/download/{imageFile}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageFile") String imageFile,
            HttpServletResponse response) throws IOException{
            var resource = this.fileService.getResource(path,imageFile);
            StreamUtils.copy(resource,response.getOutputStream());
    }


}


