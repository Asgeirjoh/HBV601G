package is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories;


import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Workout save(Workout workout);
    Workout saveAndFlush(Workout workout);
    void delete(Workout workout);
    void deleteAll();
    List<Workout> findAll();
    List<Workout> findByWorkoutName(String workoutName);
    //List<Workout> findByUser(User user);
    Optional<Workout> findById(long id);
    //List<Workout> findByWorkoutType(WorkoutType workoutType);
    boolean existsById (long id);
    boolean existsByWorkoutName (String workoutName);

}
