/*
package me.olaseo.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.olaseo.springbootdeveloper.domain.Comment;
import me.olaseo.springbootdeveloper.dto.AddArticleRequest;
import me.olaseo.springbootdeveloper.dto.AddCommentRequest;
import me.olaseo.springbootdeveloper.dto.UpdateArticleRequest;
import me.olaseo.springbootdeveloper.repository.CommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
// 서비스에서 모든 일들을 처리한다.
public class CommentService {

    private final CommentRepository commentRepository;

    // 블로그 글 추가 메서드
    public Comment save(AddCommentRequest request, String author) {

        return commentRepository.save(request.toEntity(author));
    }

     public Comment findByArticleId(String author) {
        return commentRepository.findByArticleId(author);
    }

    public void delete(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found :" + id));

        authorizeCommentAuthor(comment);
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment update(long id, UpdateArticleRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found :" + id));

        authorizeCommentAuthor(comment);
        comment.update(request.getTitle(), request.getContent());

        return comment;
    }

    private static void authorizeCommentAuthor(Comment comment) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comment.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
*/
