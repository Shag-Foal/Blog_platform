package blog.platform.controller;

import blog.platform.service.UserService;
import blog.platform.domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Auth {
    private final UserService userService;
    @Autowired
    public Auth(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, @ModelAttribute("user") User user2) {
        if (user2.getUsername().indexOf('@') == -1) {
            User user1 = userService.getUserByUsername(user2.getUsername());
            if (user1 != null && userService.equalPassword(user1, user2)) {
                user2.setEmail(user1.getEmail());
                session.setAttribute("user", user2);
                return "redirect:";
            } else return "redirect:login";
        }else {
            User user1 = userService.getUserByEmail(user2.getUsername());
            if (user1!=null && userService.equalPassword(user1,user2)){
                user2.setEmail(user1.getEmail());
                user2.setUsername(user1.getUsername());
                session.setAttribute("user",user2);
                return "redirect:";
            }
            else return "redirect:login";
        }
    }


    //------------------------------------------------------------------------------------------------------------------------//


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, HttpSession session) {
        User user1 = (User) session.getAttribute("user");
        try{
        if (user1 == null && userService.getUserByUsername(user.getUsername()) == null && userService.getUserByEmail(user.getEmail()) == null) {
            session.setAttribute("user", user);
            userService.save(user);
            return "redirect:";
        } else return "redirect:/register";
        } catch (Exception e){
            return "redirect:/register";
        }
    }


    //------------------------------------------------------------------------------------------------------------------------//


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:";
    }
}
