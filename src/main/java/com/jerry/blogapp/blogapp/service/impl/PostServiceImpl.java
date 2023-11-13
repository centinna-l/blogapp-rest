package com.jerry.blogapp.blogapp.service.impl;

import com.jerry.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.jerry.blogapp.blogapp.models.Post;
import com.jerry.blogapp.blogapp.payload.PostDto;
import com.jerry.blogapp.blogapp.payload.PostResponse;
import com.jerry.blogapp.blogapp.repository.PostRepository;
import com.jerry.blogapp.blogapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    // constructor based dependency injection
    private PostRepository postRepository;

    // we can omit @Autowired if the class only has one constructor
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);

        Post result = postRepository.save(post);

        // convert entity to DTO
        return mapToDto(result);
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pages = postRepository.findAll(pageable);

        List<Post> posts = pages.getContent();
        List<PostDto> contents = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(contents);
        postResponse.setPageNo(pages.getNumber());
        postResponse.setPageSize(pages.getSize());
        postResponse.setTotalElements(pages.getNumberOfElements());
        postResponse.setTotalPages(pages.getTotalPages());
        postResponse.setLast(pages.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post result = postRepository.save(post);
        return mapToDto(result);
    }

    @Override
    public void deletePostByID(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    private static Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    private static PostDto mapToDto(Post result) {
        PostDto converted = new PostDto();
        converted.setId(result.getId());
        converted.setTitle(result.getTitle());
        converted.setDescription(result.getDescription());
        converted.setContent(result.getContent());
        return converted;
    }
}
