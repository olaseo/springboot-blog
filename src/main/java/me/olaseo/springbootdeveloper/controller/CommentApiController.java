package me.olaseo.springbootdeveloper.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.olaseo.springbootdeveloper.domain.Comment;
import me.olaseo.springbootdeveloper.dto.*;
import me.olaseo.springbootdeveloper.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class CommentApiController {

    public final CommentService commentService;

    @Transactional
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<Comment> saveComment(@PathVariable long articleId,
                                               @RequestBody AddCommentRequest request,
                                               Principal principal) {

        request.setArticleId(articleId);
        request.setAuthor(principal.getName());
        Comment saved = commentService.save(request);
        return ResponseEntity.ok(saved);
    }
}


