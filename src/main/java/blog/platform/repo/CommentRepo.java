package blog.platform.repo;

import blog.platform.domain.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> getAllByArticleId(Long id);

    Comment getById(Long id);
}
