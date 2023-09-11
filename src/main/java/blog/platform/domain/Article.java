package blog.platform.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "articles")
@Getter
@Setter
@AllArgsConstructor
@JsonDeserialize(using = ArticleDeserializer.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "title should not be empty")
    private String title;

    @Column(nullable = false, name = "content", columnDefinition = "TEXT")
    @NotEmpty(message = "content should not be empty")
    private String content;

    @Column(name = "preview")
    private String preview;

    private Long likes;

    private Long dislikes;

    private Long views;

    @Column(name = "publish_date", nullable = false)
    private Timestamp publishDate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @OneToMany(mappedBy = "article")
    @JsonProperty("hashtags")
    private List<ArticleHashtag> articleHashtags;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    public Article(){
        this.title = "";
        this.content = "";
        this.author = null;
        this.publishDate = null;
        this.comments = null;
        this.preview = "";
    }
}
