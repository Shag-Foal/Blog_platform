package blog.platform.repo;

import blog.platform.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserRepo extends CrudRepository<User,Long> {
   User findByUsername(String name);

}
