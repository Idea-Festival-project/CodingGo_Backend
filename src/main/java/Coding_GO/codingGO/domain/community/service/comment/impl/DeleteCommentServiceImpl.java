package Coding_GO.codingGO.domain.community.service.comment.impl;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.repository.CommentRepository;
import Coding_GO.codingGO.domain.community.service.comment.DeleteCommentService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteCommentServiceImpl implements DeleteCommentService {

    private final CommentRepository commentRepository;

    @Override
    public void execute(Long commentId, Long userId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getAuthor().getUserId().equals(userId)) {
            throw new GlobalException(ErrorCode.USER_NOT_AUTHORIZED);
        }
        comment.setDeleted(true);
    }
}
