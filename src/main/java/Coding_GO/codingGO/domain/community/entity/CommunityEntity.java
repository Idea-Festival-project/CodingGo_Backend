package Coding_GO.codingGO.domain.community.entity;

import Coding_GO.codingGO.domain.community.data.constant.CommunityCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CommunityEntity {
    @Id
    @Column(nullable = false, name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category")
    CommunityCategory category;

    @Column(nullable = true, name = "title", length = 200)
    private String title;

    @Lob
    @Column(nullable = false, name = "content", columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    //댓글 리스트 매핑을 위한 것
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();
}
