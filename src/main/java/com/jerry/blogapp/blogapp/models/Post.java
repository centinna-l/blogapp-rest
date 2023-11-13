package com.jerry.blogapp.blogapp.models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {
    @Id // sets it as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generates id for us by default
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;
}
