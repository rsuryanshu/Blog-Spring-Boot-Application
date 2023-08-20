package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postid}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postid") long postid, @RequestBody CommentDto commentDto){
        CommentDto comment = commentService.createComment(postid, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postid}/comments")
    public List<CommentDto> getAllComments(@PathVariable("postid") long postid){
        List<CommentDto> allComments = commentService.getAllComments(postid);
        return allComments;
    }

    @GetMapping("/posts/{postid}/comments/{commentid}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postid") long postid,@PathVariable("commentid") long commentid){
        CommentDto commentDto = commentService.getCommentById(postid, commentid);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/posts/{postid}/comments/{commentid}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postid") long postid,@PathVariable("commentid") long commentid,@RequestBody CommentDto commentDto){
       CommentDto updatedComment= commentService.updateComment(postid,commentid,commentDto);
       return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postid}/comments/{commentid}")
    public ResponseEntity<String> deleteComment(@PathVariable("postid") long postid,@PathVariable("commentid") long commentid){
        commentService.deleteComment(postid,commentid);
        return new ResponseEntity<>("comment deleted",HttpStatus.OK);
    }

}
