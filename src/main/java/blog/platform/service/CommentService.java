package blog.platform.service;

import blog.platform.domain.Comment.Comment;
import blog.platform.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    @Autowired
    public CommentService(CommentRepo commentRepo){
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment){
        commentRepo.save(comment);
    }

    public List<Comment> getAllCommentByArticleId(Long id){
        return commentRepo.getAllByArticleIdOrderByPostDateDesc(id);
    }

    public Comment getById(Long id){
        return commentRepo.getById(id);
    }
}
