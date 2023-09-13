package blog.platform.domain.Comment;

import blog.platform.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "commentlikes")
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "isliked")
    boolean isLiked;

    public boolean getIsLiked(){
        return isLiked;
    }
    public void setIsLiked(boolean newIsLiked){
        isLiked = newIsLiked;
    }
}
