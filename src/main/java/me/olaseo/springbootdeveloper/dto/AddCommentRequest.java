package me.olaseo.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.olaseo.springbootdeveloper.domain.Article;
import me.olaseo.springbootdeveloper.domain.Comment;

import java.time.LocalDateTime;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddCommentRequest {

    private Long id;
    private Long articleId;
    private String commentContent;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment toEntity(String userName) {
        return Comment.builder()
                .content(commentContent)
                .author(author)
                .build();
    }
}
