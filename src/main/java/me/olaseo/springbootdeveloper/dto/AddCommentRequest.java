/*
package me.olaseo.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.olaseo.springbootdeveloper.domain.Article;
import me.olaseo.springbootdeveloper.domain.Comment;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddCommentRequest {

    private String content;

    public Comment toEntity(String author) {
        return Comment.builder()
                .content(content)
                .author(author)
                .build();
    }
}
*/
