package blog.platform.controller;

import blog.platform.domain.User;
import blog.platform.service.ArticleService;
import blog.platform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import blog.platform.domain.Article;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;

    public ArticleController(ArticleService articleService,UserService userService){
        this.articleService = articleService;
        this.userService = userService;
    }
    @PostMapping("/newArticle")
    @ResponseBody
    public ResponseEntity<String> newArticle(@RequestBody Article article,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            article.setAuthor(userService.getUserByUsername(user.getUsername()));
            article.setPublishDate(Timestamp.valueOf(LocalDateTime.now()));
            articleService.save(article);
            return ResponseEntity.ok("Сохранено");
        }else return ResponseEntity.badRequest().body("Пользователь не авторизован");
    }

}