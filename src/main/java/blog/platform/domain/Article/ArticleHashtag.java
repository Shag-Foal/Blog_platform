package blog.platform.domain.Article;

import blog.platform.domain.Hashtag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article_hashtag")
@Getter
@Setter
@NoArgsConstructor
public class ArticleHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Hashtag hashtag;
}