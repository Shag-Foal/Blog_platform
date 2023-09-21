package blog.platform.controller;

import blog.platform.domain.Article.Article;
import blog.platform.domain.User;
import blog.platform.service.ArticleService;
import blog.platform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

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
            model.addAttribute("sorted","дате" );
            return "index";
        }
        else return "redirect:/login";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchQuery") String name, HttpSession session, Model model){
        if (session.getAttribute("user") != null) {
            List<Article> articleList = articleService.getArticleList();
            articleList  = articleList.stream().filter(e -> e.getTitle().startsWith(name)).collect(Collectors.toList());
            model.addAttribute("articles", articleList);
            model.addAttribute("search", name);
            return "index";
        }else return "redirect:/login";
    }

    @GetMapping("/createArticle")
    public String createArticle(HttpSession session){
        if (session.getAttribute("user") != null){
            return "Article/createArticle";
        }else return "redirect:";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if (user != null){
            model.addAttribute("user",user);
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

    @GetMapping("/sortByLikes")
    public String sortByLikes (HttpSession session, Model model){
        if (session.getAttribute("user") != null) {
            model.addAttribute("articles", articleService.getArticleListSortedByLikes());
            model.addAttribute("sorted","лайкам" );
            return "index";
        }
        else return "redirect:/login";
    }

    @GetMapping("/sortByDislikes")
    public String  sortByDislikes (HttpSession session, Model model){
        if (session.getAttribute("user") != null) {
            model.addAttribute("articles", articleService.getArticleListSortedByDislikes());
            model.addAttribute("sorted","дизлайкам" );
            return "index";
        }
        else return "redirect:/login";
    }
    @GetMapping("/sortByViews")
    public String sortByViews (HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            List<Article> sortedArticles = articleService.getArticleListSortedByViews();
            model.addAttribute("articles", sortedArticles);
            model.addAttribute("sorted","просмотрам" );
            return "index";
        }
        else return "redirect:/login";
    }

}
