package com.jerry.blogapp.blogapp.payload;


import com.jerry.blogapp.blogapp.models.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String name;
    @NotEmpty(message = "email should not be null or empty")
    @Email(message = "Invalid Email")
    private String email;
    @NotEmpty(message = "Body should not be null or empty")
    private String body;
}
