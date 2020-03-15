package is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
    void delete(User user);
    void deleteAll();
    long count();
    boolean existsById (long id);
    boolean existsByUsername (String username);
    List<User> findAll();
    Optional<User> findById (long id);
    User findByUsername (String username);
}