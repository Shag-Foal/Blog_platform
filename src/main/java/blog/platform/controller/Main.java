package blog.platform.controller;

import blog.platform.domain.Article;
import blog.platform.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Main {
    private final ArticleService articleService;

    public Main(ArticleService articleService){
        this.articleService = articleService;
    }
    @GetMapping
    public String index(HttpSession session,Model model){
        if (session.getAttribute("user") == null) {
            List<Article> articlesList = articleService.getArticleList();
            model.addAttribute("articleList",articlesList);
            return "redirect:/login";
        }
        else return "index";
    }

    @GetMapping("/createArticle")
    public String createArticle(HttpSession session){
        if (session.getAttribute("user") != null){
            return "Article/createArticle";
        }else return "redirect:";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model){
        if (session.getAttribute("user") != null){
            model.addAttribute("user",session.getAttribute("user"));
            return "profile";
        }
        return "redirect:";
    }


}
