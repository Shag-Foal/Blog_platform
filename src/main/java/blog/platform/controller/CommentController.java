package blog.platform.controller;

import blog.platform.domain.Article.Article;
import blog.platform.domain.Comment.Comment;
import blog.platform.domain.Comment.CommentLikes;
import blog.platform.domain.User;
import blog.platform.service.ArticleService;
import blog.platform.service.CommentLikesService;
import blog.platform.service.CommentService;
import blog.platform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentLikesService commentLikesService;

    public CommentController(CommentService commentService,ArticleService articleService,UserService userService,CommentLikesService commentLikesService){
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
        this.commentLikesService = commentLikesService;
    }

    @PostMapping("/postComment")
    public ResponseEntity<String> postComment(@RequestBody Comment comment, HttpSession session){
        User user1 = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(user1.getUsername());
        Article article = articleService.getById(comment.getArticle().getId());
        if (user != null){
            if (article != null) {
                comment.setArticle(article);
                comment.setUser(user);
                comment.setLikes(0L);
                comment.setPostDate(Timestamp.valueOf(LocalDateTime.now()));
                commentService.save(comment);
                return ResponseEntity.ok("Comment was saved in bd");
            }
            else return ResponseEntity.badRequest().body("not found articleId");
        }
        else return ResponseEntity.badRequest().body("User not authenticated");
    }

    @PostMapping("/postReplyComment")
    public ResponseEntity<String> postReplyComment(@RequestBody Comment comment, HttpSession session){
        User user1 = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(user1.getUsername());
        User userResponse = userService.getById(comment.getResponseUser().getId());
        Article article = articleService.getById(comment.getArticle().getId());
        if (user != null){
            if (article != null) {
                comment.setArticle(article);
                comment.setUser(user);
                comment.setLikes(0L);
                comment.setResponseUser(userResponse);
                comment.setPostDate(Timestamp.valueOf(LocalDateTime.now()));
                commentService.save(comment);
                return ResponseEntity.ok("Comment was saved in bd");
            }
            else return ResponseEntity.badRequest().body("not found articleId");
        }
        else return ResponseEntity.badRequest().body("User not authenticated");
    }

    @PostMapping("/{id}/commentLike")
    public ResponseEntity<String> likeComment(HttpSession session,@PathVariable("id") Long id){
        User user1 = (User) session.getAttribute("user");
        User user = userService.getUserByUsername(user1.getUsername());
        Comment comment = commentService.getById(id);
        CommentLikes commentLikes =  commentLikesService.getByCommentIdAndUser(comment.getId(),user.getId());
        if (user != null){
            if (commentLikes != null){
                return ResponseEntity.ok(like(commentLikes,user,comment));
            }
            else {
                commentLikes = new CommentLikes();
                return ResponseEntity.ok(like(commentLikes,user,comment));
            }
        }
        else return ResponseEntity.badRequest().body("not authenticated");
    }

    private String like(CommentLikes commentLikes,User user,Comment comment){
        if (!commentLikes.getIsLiked()){
            commentLikes.setComment(comment);
            commentLikes.setUser(user);
            comment.setLikes(comment.getLikes()+1);
            commentLikes.setIsLiked(true);
            commentService.save(comment);
            commentLikesService.save(commentLikes);
            return "liked";
        }
        else {
            commentLikes.setComment(comment);
            commentLikes.setUser(user);
            comment.setLikes(comment.getLikes()-1);
            commentLikes.setIsLiked(false);
            commentService.save(comment);
            commentLikesService.save(commentLikes);
            return "already liked";
        }
    }
}
