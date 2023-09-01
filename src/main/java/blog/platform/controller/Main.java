package blog.platform.controller;

import blog.platform.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class Main {
    @GetMapping
    public String index(HttpSession session){
        if (session.getAttribute("user") == null)
            return "redirect:/login";
        else return "index";
    }

    @GetMapping("/createArticle")
    public String createArticle(HttpSession session){
        if ( (User) session.getAttribute("user") != null){
            return "Article/createArticle";
        }else return "redirect:";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model){
        if (session.getAttribute("user") != null){
            model.addAttribute("user",(User)session.getAttribute("user"));
            return "profile";
        }
        return "redirect:";
    }
}
