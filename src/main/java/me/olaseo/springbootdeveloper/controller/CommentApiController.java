package me.olaseo.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.olaseo.springbootdeveloper.domain.Comment;
import me.olaseo.springbootdeveloper.dto.*;
import me.olaseo.springbootdeveloper.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class CommentApiController {

    public final CommentService commentService;

    @PostMapping("articles/{articleId}/comments")
    public ResponseEntity<Comment> saveComment(@PathVariable("articleId") long id, @RequestBody AddCommentRequest request,
                                              Principal principal) {

        Comment savedComment = commentService.save(request, principal.getName());
        return ResponseEntity.ok()
                .body(savedComment);
    }
}


