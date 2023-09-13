package blog.platform.repo;

import blog.platform.domain.Comment.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepo extends JpaRepository<CommentLikes,Long> {
    CommentLikes getByCommentIdAndUserId(Long commentId,Long userId);
}
