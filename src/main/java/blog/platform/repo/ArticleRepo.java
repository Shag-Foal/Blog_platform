package blog.platform.repo;

import blog.platform.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article,Long> {
    Article getById(Long id);
}
