package com.jerry.blogapp.blogapp.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Post Title should have more than 2 characters.")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Descriptions should have more than 10 characters")
    private String description;
    @NotEmpty
    @Size(min = 50, message = "Your blog content should have atleast 50 characters")
    private String content;
    private Set<CommentDto> comments;

}
