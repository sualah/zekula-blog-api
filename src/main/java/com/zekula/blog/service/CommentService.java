package com.zekula.blog.service;

import com.zekula.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto  getCommentById(Long postId, Long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void  deleteComment(long postId, long commentId);


}
