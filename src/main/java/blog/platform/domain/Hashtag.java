package blog.platform.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Table(name = "hashtag")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String hashtag;

    @OneToMany(mappedBy = "hashtag")
    private List<ArticleHashtag> articleHashtags;

}
