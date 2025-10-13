package me.olaseo.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.olaseo.springbootdeveloper.domain.Article;
import me.olaseo.springbootdeveloper.domain.Comment;
import me.olaseo.springbootdeveloper.dto.AddCommentRequest;
import me.olaseo.springbootdeveloper.dto.CommentRequest;
import me.olaseo.springbootdeveloper.dto.CommentResponse;
import me.olaseo.springbootdeveloper.dto.UpdateCommentRequest;
import me.olaseo.springbootdeveloper.repository.BlogRepository;
import me.olaseo.springbootdeveloper.repository.CommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
// 서비스에서 모든 일들을 처리한다.
public class CommentService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    /*@Transactional
    public Comment save(AddCommentRequest request, String userName) {
        return commentRepository.save(request.toEntity(userName));
    }*/

    public Comment save(AddCommentRequest request) {
        // 1) articleId로 Article 조회
        Article article = blogRepository.findById(request.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 게시글 ID입니다."));

        if (request.getCommentContent() == null || request.getCommentContent().isBlank()) {
            throw new IllegalArgumentException("댓글 내용이 비어 있습니다.");
        }

        // 2) DTO의 toEntity로 Comment 생성 (author가 미리 세팅돼 있어야 함)
        Comment comment = request.toEntity(article);

        // 3) 저장 후 반환
        return commentRepository.save(comment);
    }

    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    @Transactional
    public Comment update(long id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found :" + id));

        authorizeCommentAuthor(comment);
        comment.update(request.getCommentContent(), request.getUpdatedAt());

        return comment;
    }

    public void delete(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found :" + id));

        authorizeCommentAuthor(comment);
        commentRepository.delete(comment);
    }

    public List<Comment> findAll(long articleId) {
        return commentRepository.findAll();
    }

    private static void authorizeCommentAuthor(Comment comment) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comment.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}
