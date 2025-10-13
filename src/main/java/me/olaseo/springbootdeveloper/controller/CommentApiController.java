package me.olaseo.springbootdeveloper.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.olaseo.springbootdeveloper.domain.Comment;
import me.olaseo.springbootdeveloper.dto.*;
import me.olaseo.springbootdeveloper.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> findAll(@PathVariable("articleId") long articleId) {
        List<CommentResponse> comment = commentService.findAll(articleId)
                .stream()
                .map(CommentResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(comment);
    }

    @GetMapping("/api/articles/{articleId}/comments/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable long articleId, @PathVariable long id) {
        Comment comment = commentService.findById(id);

        return ResponseEntity.ok()
                .body(new CommentResponse(comment));
    }

    @PatchMapping("/api/articles/{articleId}/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable long articleId,
                                                 @PathVariable long id,
                                                 @RequestBody UpdateCommentRequest request) {
        Comment updatedComment = commentService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedComment);
    }

    @DeleteMapping("/api/articles/{articleId}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long articleId, @PathVariable long id) {
        commentService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}


