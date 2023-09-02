package blog.platform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 symbols")
    private String username;

    private String password;

    private String email;

    @OneToMany(mappedBy = "author")
    private List<Article> articles;


    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @Override
    public String toString() {
        return username + "\n" +
                password;
    }
}
