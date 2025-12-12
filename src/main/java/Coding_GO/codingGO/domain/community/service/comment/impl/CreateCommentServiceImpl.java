package Coding_GO.codingGO.domain.community.service.comment.impl;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.entity.CommunityEntity;
import Coding_GO.codingGO.domain.community.mapper.comment.CreateCommentMapper;
import Coding_GO.codingGO.domain.community.presentation.data.request.comment.CreateCommentRequest;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.CreateCommentResponse;
import Coding_GO.codingGO.domain.community.repository.CommentRepository;
import Coding_GO.codingGO.domain.community.repository.CommunityRepository;
import Coding_GO.codingGO.domain.community.service.comment.CreateCommentService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCommentServiceImpl implements CreateCommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final CreateCommentMapper mapper;
    private final UserRepository userRepository;

    @Override
    public CreateCommentResponse execute(Long postId, Long user_id, CreateCommentRequest request) {
        UserEntity author = userRepository.findById(user_id)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND_EXCEPTION));

        CommunityEntity community = communityRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMUNITY_NOT_FOUND_EXCEPTION));

        Long parentCommentId = request.parentCommentId();
        CommentEntity parentComment = null;

        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_PARENT_COMMENT));
            if (parentComment.is_deleted()) {
                throw new GlobalException(ErrorCode.PARENT_COMMENT_IS_DELETED);
            }
        }
        CommentEntity comment = CommentEntity.builder()
                .content(request.content())
                .author(author)
                .post(community)
                .parentComment(parentComment)
                .is_deleted(false)
                .build();
        CommentEntity save = commentRepository.save(comment);
        return mapper.toResponse(save);
    }
}
