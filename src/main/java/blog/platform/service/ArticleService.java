package blog.platform.service;

import blog.platform.domain.Article;
import blog.platform.repo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepo articleRepo;
    @Autowired
    public ArticleService(ArticleRepo articleRepo){
        this.articleRepo = articleRepo;
    }

    public void save(Article article){
        articleRepo.save(article);
    }

    public Article getById(Long id){
        return articleRepo.getById(id);
    }

}
