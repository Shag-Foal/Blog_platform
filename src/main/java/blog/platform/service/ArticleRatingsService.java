package blog.platform.service;

import blog.platform.domain.Article.ArticleRatings;
import blog.platform.repo.ArticleRatingsRepo;
import org.springframework.stereotype.Service;

@Service
public class ArticleRatingsService {

    private final ArticleRatingsRepo articleRatingsRepo;
    public ArticleRatingsService(ArticleRatingsRepo articleRatingsRepo){
        this.articleRatingsRepo = articleRatingsRepo;
    }
    public void save(ArticleRatings articleRatings){
        articleRatingsRepo.save(articleRatings);
    }

    public ArticleRatings getArticleRatings_ByArticleIdAndUserId(Long articleId,Long userId) {
        return articleRatingsRepo.findByArticleIdAndUserId(articleId,userId);
    }
}
