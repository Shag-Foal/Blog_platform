package blog.platform.repo;

import blog.platform.domain.ArticleRatings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRatingsRepo  extends JpaRepository<ArticleRatings,Long> {
    ArticleRatings findByArticleIdAndUserId(Long articleId,Long userId);
}
