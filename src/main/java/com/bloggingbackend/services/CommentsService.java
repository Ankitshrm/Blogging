package com.bloggingbackend.services;

import com.bloggingbackend.payloads.CommentsDTO;

public interface CommentsService {

    CommentsDTO createComment(CommentsDTO commentsDTO, Integer postId);

    void deleteComment(Integer commentId);
}
