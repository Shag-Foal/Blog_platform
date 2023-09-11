package blog.platform.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "articleratings")
public class ArticleRatings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private ActionType actionType;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}

