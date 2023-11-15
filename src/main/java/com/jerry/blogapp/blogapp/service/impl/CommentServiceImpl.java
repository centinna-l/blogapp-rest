package com.jerry.blogapp.blogapp.service.impl;

import com.jerry.blogapp.blogapp.exceptions.BlogApiException;
import com.jerry.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.jerry.blogapp.blogapp.models.Comment;
import com.jerry.blogapp.blogapp.models.Post;
import com.jerry.blogapp.blogapp.payload.CommentDto;
import com.jerry.blogapp.blogapp.payload.PostDto;
import com.jerry.blogapp.blogapp.repository.CommentRepository;
import com.jerry.blogapp.blogapp.repository.PostRepository;
import com.jerry.blogapp.blogapp.service.CommentService;
import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment result = commentRepository.save(comment);

        return mapToDto(result);
    }

    @Override
    public List<CommentDto> getALlComments(long postID, int pageNo, int paeSize, String sortBy, String sortDir) {
        List<Comment> comments = commentRepository.findByPostId(postID);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }


    @Override
    public void deleteComment(Long postID, Long commentID) {
        Post post = postRepository.findById(postID).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postID));

        Comment comment = commentRepository.findById(commentID).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentID));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post and comment are not associated!");
        }

        commentRepository.deleteById(commentID);
    }

    @Override
    public List<CommentDto> getALlCommentsByName(String name) {
        System.out.println(name);
        List<Comment> comments = commentRepository.findCommentsByName("Jerry Joy");
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentByID(Long postID, Long commentID) {
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));

        //
        Comment comment = commentRepository.findById(commentID).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentID));

        System.out.println(comment.getPost().getId() + " Post : " + post.getId());
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post and Comments are not associated.");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentByID(Long postID, Long commentID, CommentDto commentDto) {
        Post post = postRepository.findById(postID).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postID));

        Comment comment = commentRepository.findById(commentID).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentID));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post and comment are not associated!");
        }
        System.out.println("Name: " + commentDto.getName());
        comment.setName(commentDto.getName());
        comment.setEmail(comment.getEmail());
        comment.setBody(commentDto.getBody());

        Comment result = commentRepository.save(comment);
        return mapToDto(result);
    }


    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
