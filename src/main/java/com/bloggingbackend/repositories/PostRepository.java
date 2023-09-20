package com.bloggingbackend.repositories;

import com.bloggingbackend.models.Category;
import com.bloggingbackend.models.Post;
import com.bloggingbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
