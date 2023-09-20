package com.bloggingbackend.repositories;


import com.bloggingbackend.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
}
