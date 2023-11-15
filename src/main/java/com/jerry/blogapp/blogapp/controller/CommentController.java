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

    @PostMapping("/post/{postID}/comment")
    public ResponseEntity<CommentDto> createPost(
            @PathVariable("postID") Long postID,
            @RequestBody CommentDto commentDto
    ) {
        System.out.println(commentDto.getName() + " " + commentDto.getEmail() + " " + commentDto.getBody());
        return new ResponseEntity<CommentDto>(commentService.createComment(postID, commentDto), HttpStatus.CREATED);
    }


    @GetMapping("/post/{postID}/comment")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(
            @PathVariable("postID") Long postID
    ) {

        return new ResponseEntity<List<CommentDto>>(commentService.getALlComments(postID, 0, 0, "", ""), HttpStatus.OK);
    }


    @GetMapping("/post/{postID}/comment/name")
    public ResponseEntity<List<CommentDto>> getAllCommentsByName(
            @RequestBody String name
    ) {
        System.out.println(name);
        return new ResponseEntity<List<CommentDto>>(commentService.getALlCommentsByName(name), HttpStatus.OK);
    }

    @GetMapping("/post/{postID}/comment/{commentID}")
    public ResponseEntity<CommentDto> getCommentbyID(
            @PathVariable("postID") Long postID,
            @PathVariable("commentID") Long commentID) {
        return new ResponseEntity<>(commentService.getCommentByID(postID, commentID), HttpStatus.OK);
    }


    @PutMapping("/post/{postID}/comment/{commentID}")
    public ResponseEntity<CommentDto> updateCommentByID(
            @PathVariable("postID") Long postID,
            @PathVariable("commentID") Long commentID,
            @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateCommentByID(postID, commentID, commentDto), HttpStatus.OK);
    }


    @DeleteMapping("/post/{postID}/comment/{commentID}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postID") Long postID,
            @PathVariable("commentID") Long commentID) {
        commentService.deleteComment(postID, commentID);
        return new ResponseEntity<>("Comment Deleted!", HttpStatus.OK);
    }
}
