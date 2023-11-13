package com.jerry.blogapp.blogapp.payload;


import com.jerry.blogapp.blogapp.models.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
