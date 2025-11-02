package me.olaseo.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.olaseo.springbootdeveloper.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentResponse {
    private long id;
    private String commentContent;
    private String author;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.author = comment.getAuthor();
    }
}
