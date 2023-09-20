package com.bloggingbackend.services.impl;

import com.bloggingbackend.exceptions.ResourceNotFoundException;
import com.bloggingbackend.models.Category;
import com.bloggingbackend.models.Post;
import com.bloggingbackend.models.User;
import com.bloggingbackend.payloads.PostDTO;
import com.bloggingbackend.payloads.PostResponse;
import com.bloggingbackend.repositories.CategoryRepository;
import com.bloggingbackend.repositories.PostRepository;
import com.bloggingbackend.repositories.UserRepository;
import com.bloggingbackend.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user  = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->  new ResourceNotFoundException("Category","CategoryId",categoryId));

        Post post = this.modelMapper.map(postDTO,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        return this.modelMapper.map(savedPost,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        post.setImageName(postDTO.getImageName());
        post.setAddedDate(new Date());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        Post updatePost = this.postRepository.save(post);
        return this.modelMapper.map(updatePost,PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->  new ResourceNotFoundException("Post", "PostID",postId));
        return this.modelMapper.map(post,PostDTO.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String dir) {
        Sort sort =null;
        if(dir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage =this.postRepository.findAll(p);
        List<Post> posts = postPage.getContent();
        List<PostDTO> postDTOS = posts.stream().map(it -> this.modelMapper.map(it,PostDTO.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPostsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        List<Post> posts = this.postRepository.findByUser(user);
        return posts.stream().map(it -> this.modelMapper.map(it,PostDTO.class)).toList();
    }

    @Override
    public List<PostDTO> getAllPostsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Post> categories = this.postRepository.findByCategory(category);
        return categories.stream().map(it -> this.modelMapper.map(it, PostDTO.class)).toList();
    }

    @Override
    public List<PostDTO> searchPost(String keyword) {
        List<Post> posts = this.postRepository.searchByTitle(keyword);
        List<PostDTO> postDTOS = posts.stream().map(it ->  this.modelMapper.map(it,PostDTO.class)).toList();
        return postDTOS;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post =postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        this.postRepository.delete(post);
    }
}
