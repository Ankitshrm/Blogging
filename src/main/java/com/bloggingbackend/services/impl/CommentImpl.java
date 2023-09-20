package com.bloggingbackend.services.impl;

import com.bloggingbackend.exceptions.ResourceNotFoundException;
import com.bloggingbackend.models.Comments;
import com.bloggingbackend.models.Post;
import com.bloggingbackend.payloads.CommentsDTO;
import com.bloggingbackend.payloads.PostResponse;
import com.bloggingbackend.repositories.CommentsRepository;
import com.bloggingbackend.repositories.PostRepository;
import com.bloggingbackend.services.CommentsService;
import com.bloggingbackend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentImpl implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepository postRepository;


    @Override
    public CommentsDTO createComment(CommentsDTO commentsDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));
        Comments comments =this.modelMapper.map(commentsDTO, Comments.class);
        comments.setPost(post);
        Comments savedComments = this.commentsRepository.save(comments);
        return this.modelMapper.map(savedComments,CommentsDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comments comments = this.commentsRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","commentsId",commentId));
        this.commentsRepository.delete(comments);
    }
}
