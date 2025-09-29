package me.olaseo.springbootdeveloper.dto;

import lombok.Getter;
import me.olaseo.springbootdeveloper.domain.Article;
import me.olaseo.springbootdeveloper.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private Article articleId;
    private String commentContent;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponse(Comment comment) {
        this.commentContent = comment.getCommentContent();
    }
}
