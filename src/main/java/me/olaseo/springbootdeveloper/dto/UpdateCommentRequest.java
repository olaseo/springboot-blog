package me.olaseo.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateCommentRequest {
    private long id;
    private String content;
    private LocalDateTime updatedAt;
}
