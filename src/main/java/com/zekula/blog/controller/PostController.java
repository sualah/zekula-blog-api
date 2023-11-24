package com.zekula.blog.controller;

import com.zekula.blog.payload.PostDto;
import com.zekula.blog.payload.PostResponse;
import com.zekula.blog.service.PostService;
import com.zekula.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
   private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto thePostDto) {

        return new ResponseEntity<>(postService.createPost(thePostDto), HttpStatus.CREATED);
    }

    @GetMapping
    PostResponse getAllPosts(
           @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
           @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
           @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

    ) {

        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
   }

    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<PostDto> updatePost(@RequestBody PostDto thePostDto, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.updatePost(thePostDto,id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok("Post with id: " + id + " deleted successfully.");
    }
}
