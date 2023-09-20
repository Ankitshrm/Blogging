package com.bloggingbackend.payloads;

import com.bloggingbackend.models.Comments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Integer postid;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private UserDTO user;
    private CategoryDTO category;

    private Set<CommentsDTO> comments = new HashSet<>();

}
