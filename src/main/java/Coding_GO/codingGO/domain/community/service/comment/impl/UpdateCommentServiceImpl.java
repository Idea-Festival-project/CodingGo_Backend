package Coding_GO.codingGO.domain.community.service.comment.impl;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.mapper.comment.CreateCommentMapper;
import Coding_GO.codingGO.domain.community.presentation.data.request.comment.UpdateCommentRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.CreateCommentResponse;
import Coding_GO.codingGO.domain.community.repository.CommentRepository;
import Coding_GO.codingGO.domain.community.service.comment.UpdateCommentService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UpdateCommentServiceImpl implements UpdateCommentService {

    private final CommentRepository commentRepository;
    private final CreateCommentMapper mapper;

    @Override
    public CreateCommentResponse execute(Long commentId, UpdateCommentRequest request, Long userId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(()->new GlobalException(ErrorCode.NOT_FOUND_PARENT_COMMENT));

        if(!comment.getAuthor().getUserId().equals(userId)){
            throw new GlobalException(ErrorCode.USER_NOT_AUTHORIZED);
        }

        if(comment.is_deleted()){
            throw new GlobalException(ErrorCode.CANNOT_UPDATE_DELETED_COMMENT);
        }
        comment.setContent(request.content());

        return mapper.toResponse(comment);
    }
}
