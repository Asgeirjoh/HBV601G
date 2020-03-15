package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    void delete(User user);
    void deleteAll();
    long count();
    boolean existsById(long id);
    boolean existsByUsername(String username);
    List<User> findAll();
    Optional<User> findById(long id);
    User findByUsername(String username);
    User login(User user);
}