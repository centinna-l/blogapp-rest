package com.jerry.blogapp.blogapp.repository;

import com.jerry.blogapp.blogapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);

    List<Comment> findCommentsByName(String name);
}
