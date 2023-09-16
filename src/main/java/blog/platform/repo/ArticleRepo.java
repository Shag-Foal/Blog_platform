package blog.platform.repo;

import blog.platform.domain.Article.Article;
import blog.platform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article,Long> {
    Article findArticleByIdOrderByPublishDateDesc(Long id);

    List<Article> findAllByAuthorOrderByPublishDateDesc(User user);

    List<Article> findAllByOrderByPublishDateDesc();
}
