package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post newPost = postRepository.save(modelMapper.map(postDto,Post.class));
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public List<PostDto> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> listOfPosts = postRepository.findAll(pageable);
        List<Post> posts=listOfPosts.getContent();
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id :" + id)
        );
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostDto updatePost(long postid, PostDto postDto) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id :" + postid)
        );
        modelMapper.map(postDto,post);
       // post.setId(postid);
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public PostDto deletePostById(long id) {
        Post deletedPost = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id :" + id)
        );
        postRepository.deleteById(id);
        return modelMapper.map(deletedPost,PostDto.class);
    }

}
