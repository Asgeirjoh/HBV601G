package is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Exercise save(Exercise exercise);
    void delete(Exercise exercise);
    void deleteAll();
    boolean existsById(Long id);
    boolean existsByName(String name);
    Optional<Exercise> findById(Long id);
    List<Exercise> findByName(String name);
    List<Exercise> findAll();
}
