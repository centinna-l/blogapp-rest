package com.jerry.blogapp.blogapp.controller;


import com.jerry.blogapp.blogapp.payload.CommentDto;
import com.jerry.blogapp.blogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postID}/comment")
    public ResponseEntity<CommentDto> createPost(
            @PathVariable("postID") Long postID,
            @RequestBody CommentDto commentDto
    ) {
        System.out.println(commentDto.getName() + " " + commentDto.getEmail() + " " + commentDto.getBody());
        return new ResponseEntity<CommentDto>(commentService.createComment(postID, commentDto), HttpStatus.CREATED);
    }


    @GetMapping("/posts/{postID}/comment")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(
            @PathVariable("postID") Long postID
    ) {

        return new ResponseEntity<List<CommentDto>>(commentService.getALlComments(postID, 0, 0, "", ""), HttpStatus.OK);
    }


    @GetMapping("/posts/{postID}/comment/name")
    public ResponseEntity<List<CommentDto>> getAllCommentsByName(
            @RequestBody String name
    ) {
        System.out.println(name);
        return new ResponseEntity<List<CommentDto>>(commentService.getALlCommentsByName(name), HttpStatus.OK);
    }
}
