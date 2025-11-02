package me.olaseo.springbootdeveloper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.olaseo.springbootdeveloper.domain.Article;
import me.olaseo.springbootdeveloper.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCommentRequest {

    private Long articleId;

    private String author;

    @NotNull
    private String commentContent;

    public Comment toEntity(Article articleId) {
        return new Comment(
                articleId,
                this.commentContent,
                this.author
        );
    }
}
