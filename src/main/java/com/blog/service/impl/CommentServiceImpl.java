package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postid, CommentDto commentDto) {
        Post post = postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post not found for id" + postid));
        Comment comment = modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedcomment = commentRepository.save(comment);
        return modelMapper.map(savedcomment,CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllComments(long postid) {
        postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post not found for id" + postid));
        List<Comment> allComments = commentRepository.findByPostId(postid);
        return allComments.stream().map(allComment -> modelMapper.map(allComment,CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postid, long commentid) {
        postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post not found for id" + postid));
        Comment comment = commentRepository.findById(commentid).orElseThrow(() -> new ResourceNotFoundException("comment not found with comment id :" + commentid));
        return modelMapper.map(comment,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(long postid, long commentid, CommentDto commentDto) {
        postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post not found for id" + postid));
        Comment comment = commentRepository.findById(commentid).orElseThrow(() -> new ResourceNotFoundException("comment not found with comment id :" + commentid));
        modelMapper.map(commentDto,comment);
        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(long postid, long commentid) {
        postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post not found for id" + postid));
        Comment comment = commentRepository.findById(commentid).orElseThrow(() -> new ResourceNotFoundException("comment not found with comment id :" + commentid));

        commentRepository.deleteById(commentid);
    }

}
