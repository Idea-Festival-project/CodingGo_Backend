package Coding_GO.codingGO.domain.community.service.comment.impl;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.mapper.comment.GetCommentMapper;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.GetCommentResponse;
import Coding_GO.codingGO.domain.community.repository.CommentRepository;
import Coding_GO.codingGO.domain.community.repository.CommunityRepository;
import Coding_GO.codingGO.domain.community.service.comment.GetCommentService;
import Coding_GO.codingGO.global.exception.ErrorCode;
import Coding_GO.codingGO.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetCommentServiceImpl implements GetCommentService {

    private final CommentRepository CommentRepository;
    private final GetCommentMapper mapper;
    private final CommunityRepository communityRepository;

    @Override
    public List<GetCommentResponse> execute(Long postId) {

        validateCommunityExists(postId);

        List<CommentEntity> rootComments
                = CommentRepository.findCommentsByPostIdWithAuthor(postId);

        return mapper.buildCommentTree(rootComments);
    }

    private void validateCommunityExists(Long postId) {
        if (!communityRepository.existsById(postId)) {
            throw new GlobalException(ErrorCode.COMMUNITY_NOT_FOUND_EXCEPTION);
        }
    }
}
