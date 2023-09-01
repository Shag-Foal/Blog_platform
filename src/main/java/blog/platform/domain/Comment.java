package blog.platform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "content should not be empty")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "response_user", referencedColumnName = "id")
    private User responseUser;

    @Column(name = "post_date", nullable = false)
    private Timestamp postDate;

    @Override
    public String toString(){
        return "Comment{" + content + ";" + postDate + ";" + article.getAuthor() + ";}";
    }
}
