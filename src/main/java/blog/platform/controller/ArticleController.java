package blog.platform.controller;

import blog.platform.domain.*;
import blog.platform.domain.Article.ActionType;
import blog.platform.domain.Article.Article;
import blog.platform.domain.Article.ArticleRatings;
import blog.platform.service.ArticleRatingsService;
import blog.platform.service.ArticleService;
import blog.platform.service.CommentService;
import blog.platform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final ArticleRatingsService articleRatingsService;
    private final ArticleService articleService;
    private final UserService userService;
    private final CommentService commentService;

    public ArticleController(ArticleService articleService, ArticleRatingsService articleRatingsService, UserService userService,CommentService commentService) {
        this.articleService = articleService;
        this.articleRatingsService = articleRatingsService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String article(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            Article article = articleService.getById(id);
            if (article != null) {
                article.setViews(article.getViews() + 1);
                articleService.save(article);
                model.addAttribute("article", article);
                model.addAttribute("comments",commentService.getAllCommentByArticleId(article.getId()));
                return "Article/article";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> like(@PathVariable("id") Long id, HttpSession session) {
        User user1 = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(user1.getUsername());
        ArticleRatings articleRatings = articleRatingsService.getArticleRatings_ByArticleIdAndUserId(id, user.getId());
        Article article = articleService.getById(id);
        if (articleRatings != null) {
            return ResponseEntity.ok(like(article, articleRatings, user));
        } else {
            article.setLikes(article.getLikes() + 1);
            articleRatings = new ArticleRatings();
            articleRatings.setActionType(ActionType.like);
            articleRatings.setUser(user);
            articleRatings.setArticle(article);
            articleService.save(article);
            articleRatingsService.save(articleRatings);
            return ResponseEntity.ok("Liked");
        }
    }

    private String like(Article article, ArticleRatings articleRatings, User user) {
        if (articleRatings.getActionType() == ActionType.dislike) {
            article.setLikes(article.getLikes() + 1);
            article.setDislikes(article.getDislikes() - 1);
            articleRatings.setActionType(ActionType.like);
            articleRatings.setUser(user);
            articleRatings.setArticle(article);
            articleService.save(article);
            articleRatingsService.save(articleRatings);
            return "Swapped";
        } else {
            article.setLikes(article.getLikes() - 1);
            articleRatings.setActionType(ActionType.like);
            articleRatings.setUser(user);
            articleRatings.setArticle(article);
            articleService.save(article);
            articleRatingsService.save(articleRatings);
            return "removeLike";
        }
    }


    @PostMapping("/{id}/dislike")
    public ResponseEntity<String> dislike(@PathVariable("id") Long id, HttpSession session) {
        User user1 = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(user1.getUsername());
        ArticleRatings articleRatings = articleRatingsService.getArticleRatings_ByArticleIdAndUserId(id, user.getId());
        Article article = articleService.getById(id);
        if (articleRatings != null) {
            return ResponseEntity.ok(dislike(article, articleRatings, user));
        } else {
            articleRatings = new ArticleRatings();
            article.setDislikes(article.getDislikes() + 1);
            articleRatings.setActionType(ActionType.dislike);
            articleRatings.setUser(user);
            articleRatings.setArticle(article);
            articleService.save(article);
            articleRatingsService.save(articleRatings);
            return ResponseEntity.ok("Disliked");
        }
    }

    private String dislike(Article article, ArticleRatings articleRatings, User user) {
        if (articleRatings.getActionType() == ActionType.like) {
            article.setLikes(article.getLikes() - 1);
            article.setDislikes(article.getDislikes() + 1);
            articleRatings.setActionType(ActionType.dislike);
            articleRatings.setUser(user);
            articleRatings.setArticle(article);
            articleService.save(article);
            articleRatingsService.save(articleRatings);
            return "Swapped";
        } else {
            article.setDislikes(article.getDislikes() - 1);
            articleRatings.setActionType(ActionType.dislike);
            articleRatings.setUser(user);
            articleRatings.setArticle(article);
            articleService.save(article);
            articleRatingsService.save(articleRatings);
            return "removeDislike";
        }
    }
}
