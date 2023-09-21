package blog.platform.controller;

import blog.platform.service.ArticleService;
import blog.platform.service.UserService;
import org.springframework.stereotype.Controller;


@Controller
public class ArticlesSortController {
    private final ArticleService articleService;
    private final UserService userService;
    public ArticlesSortController(ArticleService articleService,UserService userService){
        this.articleService = articleService;
        this.userService = userService;
    }


}
