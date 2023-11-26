package com.zekula.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should at least be 2 characters.")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post title should at least be 10 characters.")
    private String description;

    @NotEmpty
    private String content;


    private Set<CommentDto> comments;

}
