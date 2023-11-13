package com.jerry.blogapp.blogapp.service;

import com.jerry.blogapp.blogapp.payload.PostDto;
import com.jerry.blogapp.blogapp.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePostByID(Long id);
}
