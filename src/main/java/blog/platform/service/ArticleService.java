package blog.platform.service;

import blog.platform.domain.Article.Article;
import blog.platform.domain.User;
import blog.platform.repo.ArticleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;
    public ArticleService(ArticleRepo articleRepo){
        this.articleRepo = articleRepo;
    }

    public void save(Article article){
        articleRepo.save(article);
    }

    public Article getById(Long id){
        return articleRepo.findArticleByIdOrderByPublishDateDesc(id);
    }

    public List<Article> getArticleList(){
        return articleRepo.findAllByOrderByPublishDateDesc();
    }

    public List<Article> getArticleListByUser(User user){
        return articleRepo.findAllByAuthorOrderByPublishDateDesc(user);
    }

}
