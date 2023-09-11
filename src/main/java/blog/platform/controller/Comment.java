package blog.platform.controller;

import blog.platform.domain.Article;
import blog.platform.service.ArticleService;
import blog.platform.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Comment {
    private final CommentService commentService;

    public Comment(CommentService commentService){
        this.commentService = commentService;
    }

}
