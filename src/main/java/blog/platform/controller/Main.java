package blog.platform.controller;

import blog.platform.domain.Article;
import blog.platform.domain.User;
import blog.platform.service.ArticleService;
import blog.platform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class Main {
    private final ArticleService articleService;
    private final UserService userService;

    public Main(ArticleService articleService,UserService userService){
        this.articleService = articleService;
        this.userService = userService;
    }
    @GetMapping
    public String index(HttpSession session,Model model){
        if (session.getAttribute("user") != null) {
            List<Article> articlesList = articleService.getArticleList();
            model.addAttribute("articles",articlesList);
            return "index";
        }
        else return "redirect:/login";
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
            return "/profile/profile";
        }
        return "redirect:";
    }

    @GetMapping("/profile/articles")
    public String getUserArticles(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        if (user != null){
            model.addAttribute("articles",articleService.getArticleListByUser(userService.getUserByUsername(user.getUsername())));
            return "profile/user-articles";
        }
        else return "redirect:/login";
    }
}
