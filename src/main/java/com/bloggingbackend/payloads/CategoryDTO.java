package com.bloggingbackend.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {


    private Integer categoryId;
    @NotEmpty
    @Size(min = 5,max = 15, message = "Category should be more than 5 and less than 15 characters")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 5,max = 150, message = "Description should be more than 5 and less than 150 characters")
    private String categoryDescription;

}
