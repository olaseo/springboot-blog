package me.olaseo.springbootdeveloper.dto;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.olaseo.springbootdeveloper.domain.Article;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {
    private Long id;
    private Long articleId;
    private String commentContent;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
