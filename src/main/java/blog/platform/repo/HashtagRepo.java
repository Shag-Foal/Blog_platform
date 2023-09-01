package blog.platform.repo;

import blog.platform.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepo extends JpaRepository<Hashtag,Long> {
}
