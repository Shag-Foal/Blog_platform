package blog.platform.controller;

import blog.platform.domain.Article.UpdateArticle;
import blog.platform.domain.User;
import blog.platform.service.ArticleService;
import blog.platform.service.UserService;
import jakarta.servlet.http.HttpSession;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import blog.platform.domain.Article.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class ArticleRestController {
    private final ArticleService articleService;
    private final UserService userService;

    public ArticleRestController(ArticleService articleService, UserService userService){
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
            article.setLikes(0L);
            article.setDislikes(0L);
            article.setViews(0L);
            articleService.save(article);
            return ResponseEntity.ok("Сохранено");
        }else return ResponseEntity.badRequest().body("Пользователь не авторизован");
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public String handleImageUpload(@RequestParam("image") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadDir = "C:\\Users\\baskh\\OneDrive\\Документы\\GitHub\\Blog_platform\\src\\main\\resources\\static\\uploads";
                String fileName = file.getOriginalFilename();
                String filePath = uploadDir + File.separator + fileName;
                File dest = new File(filePath);

                Thumbnails.of(file.getInputStream())
                        .size(150, 120)
                        .toFile(dest);

                return "/uploads/" + fileName;
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при загрузке или обработке изображения.");
            }
        } else {
            throw new RuntimeException("Выберите изображение для загрузки.");
        }
    }

    @PutMapping("/updateInfo")
    @ResponseBody
    public ResponseEntity<String> newArticle(@RequestBody UpdateArticle updateArticle, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Article article = articleService.getById(updateArticle.getArticleId());
            article.setLikes(updateArticle.getLikes());
            article.setDislikes(updateArticle.getDislikes());
            articleService.save(article);
            return ResponseEntity.ok("Обновлено");
        }else return ResponseEntity.badRequest().body("Пользователь не авторизован");
    }
}
