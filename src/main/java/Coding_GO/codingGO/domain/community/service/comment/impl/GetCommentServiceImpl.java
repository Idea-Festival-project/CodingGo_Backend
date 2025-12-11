package Coding_GO.codingGO.domain.community.service.comment.impl;

import Coding_GO.codingGO.domain.community.entity.CommentEntity;
import Coding_GO.codingGO.domain.community.mapper.comment.GetCommentMapper;
import Coding_GO.codingGO.domain.community.presentation.data.response.comment.GetCommentResponse;
import Coding_GO.codingGO.domain.community.repository.CommentRepository;
import Coding_GO.codingGO.domain.community.service.comment.GetCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetCommentServiceImpl implements GetCommentService {

    private final CommentRepository commentRepository;
    private final GetCommentMapper mapper;

    @Override
    public List<GetCommentResponse> execute(Long postId) {
        List<CommentEntity> comment = commentRepository.findCommentsByPostId(postId);
        if (comment == null || comment.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.buildCommentTree(comment);
    }
}
