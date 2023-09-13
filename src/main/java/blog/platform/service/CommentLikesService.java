package blog.platform.service;

import blog.platform.domain.Comment.CommentLikes;
import blog.platform.repo.CommentLikesRepo;
import org.springframework.stereotype.Service;

@Service
public class CommentLikesService {
    private final CommentLikesRepo commentLikesRepo;

    public CommentLikesService(CommentLikesRepo commentLikesRepo){
        this.commentLikesRepo = commentLikesRepo;
    }

    public CommentLikes getByCommentIdAndUser(Long commentId,Long userId){
        return commentLikesRepo.getByCommentIdAndUserId(commentId,userId);
    }

    public void save(CommentLikes commentLikes){
        commentLikesRepo.save(commentLikes);
    }
}
