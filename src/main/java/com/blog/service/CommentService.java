package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postid, CommentDto commentDto);

    List<CommentDto> getAllComments(long postid);

    CommentDto getCommentById(long postid, long commentid);

    CommentDto updateComment(long postid, long commentid, CommentDto commentDto);

    void deleteComment(long postid, long commentid);
}
