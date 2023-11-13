package com.jerry.blogapp.blogapp.service;


import com.jerry.blogapp.blogapp.models.Comment;
import com.jerry.blogapp.blogapp.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentService {


    CommentDto createComment(Long postId, CommentDto commentDto);

    List<CommentDto> getALlComments(long postID, int pageNo, int paeSize, String sortBy, String sortDir);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);

    List<CommentDto> getALlCommentsByName(String name);
}
