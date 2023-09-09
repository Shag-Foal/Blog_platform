package blog.platform.repo;

import blog.platform.domain.Article;
import blog.platform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article,Long> {
    Article findArticleById(Long id);

    List<Article> findAllByAuthor(User user);

}
