package blog.platform.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Article {
    @PostMapping("/newArticle")
    @ResponseBody
    public String newArticle(){
        return null;
    }
}
