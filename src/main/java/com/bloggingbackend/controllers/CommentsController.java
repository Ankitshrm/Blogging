package com.bloggingbackend.controllers;

import com.bloggingbackend.models.Comments;
import com.bloggingbackend.payloads.ApiResponse;
import com.bloggingbackend.payloads.CommentsDTO;
import com.bloggingbackend.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentsDTO> createComments(@RequestBody CommentsDTO commentsDTO, @PathVariable("postId") Integer postId){
        CommentsDTO comments =this.commentsService.createComment(commentsDTO,postId);
        return new ResponseEntity<>(comments, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComments( @PathVariable("commentId") Integer commentId){
        this.commentsService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comments have been deleted",true), HttpStatus.OK);
    }
}
