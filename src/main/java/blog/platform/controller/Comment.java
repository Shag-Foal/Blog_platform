package blog.platform.controller;

import blog.platform.domain.Article;
import blog.platform.service.ArticleService;
import blog.platform.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Comment {
    private final ArticleService articleService;
    private final CommentService commentService;

    public Comment(ArticleService articleService,CommentService commentService){
        this.articleService = articleService;
        this.commentService = commentService;
    }
    @GetMapping("/{id}")
    public String article(@PathVariable("id") Long id, HttpSession session, Model model){
        if (session.getAttribute("user") != null){
            Article article = articleService.getById(id);
            if (article != null) {
                model.addAttribute("article", article);
                return "Article/article";
            }
        }
        return "redirect:/login";
    }
}
