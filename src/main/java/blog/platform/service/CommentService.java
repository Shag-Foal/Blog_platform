package blog.platform.service;

import blog.platform.domain.Comment;
import blog.platform.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
