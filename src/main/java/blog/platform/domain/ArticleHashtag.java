package blog.platform.domain;

import blog.platform.domain.Article;
import blog.platform.domain.Hashtag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article_hashtag")
@Getter
@Setter
public class ArticleHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;
}