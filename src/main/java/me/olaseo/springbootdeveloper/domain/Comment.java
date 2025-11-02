package me.olaseo.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity // 엔티티로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment { // Article 객체 생성

    @Id // id 필드를 기본키로 지정.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(/*fetch = FetchType.LAZY,*/optional = false)
    @JoinColumn(
            name = "articleId",      // DB 컬럼명 관례에 맞춰서_ snake_case
            nullable = false          // not null 제약
    )
    private Article articleId;

    @Column(name = "commentContent", nullable = false)
    private String commentContent;

    @Column(name = "author", nullable = false)
    private String author;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder // 빌더 패턴으로 객체 생성
    public Comment(Article articleId, String commentContent, String author) {
        this.articleId = articleId;
        this.commentContent = commentContent;
        this.author = author;
    }

    // 내용 변경시 this로 변경.
   public void update(String commentContent, LocalDateTime updatedAt) {
        this.commentContent = commentContent;
        this.updatedAt = updatedAt;
    }
}
