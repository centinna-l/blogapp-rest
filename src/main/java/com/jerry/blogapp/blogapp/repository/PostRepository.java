package com.jerry.blogapp.blogapp.repository;

import com.jerry.blogapp.blogapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;


// No need of repository annotations - JpaRepository extends crudrepository, so @repositoy is automatically annotated.
public interface PostRepository extends JpaRepository<Post, Long> {
    //
}
