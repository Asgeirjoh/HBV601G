package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    Exercise save(Exercise exercise);
    void delete(Exercise exercise);
    void deleteAll();
    boolean existsById(long id);
    boolean existsByName(String name);
    Optional<Exercise> findById(long id);
    List<Exercise> findByName(String name);
    List<Exercise> findAll();
}
