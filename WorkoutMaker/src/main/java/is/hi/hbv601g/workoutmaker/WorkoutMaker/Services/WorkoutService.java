package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Workout;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.WorkoutLineItem;

import java.util.List;
import java.util.Optional;

public interface WorkoutService {
    Workout saveWorkout(Workout workout);
    Workout updateWorkout(long wId, Workout workout);
    Workout saveAndFlushWorkout(Workout workout);
    WorkoutLineItem saveWLI(WorkoutLineItem wli);
    WorkoutLineItem saveAndFlushWLI(WorkoutLineItem wli);
    void deleteWorkout(Workout workout);
    void deleteWLI(WorkoutLineItem wli);
    void deleteAllWorkouts();
    void deleteAllWLI();
    Workout addToWorkout(long workoutId, WorkoutLineItem workoutLineItem);
    Workout rmFromWorkout(long workoutId, int lineNumber);
    WorkoutLineItem setExerciseReps(WorkoutLineItem wli, int reps);
    WorkoutLineItem setExerciseSets(WorkoutLineItem wli, int sets);
    List<Workout> findAll();
    List<Workout> findByWorkoutName(String workoutName);
    List<Workout> findByUser(User user);
    Optional<Workout> findWorkoutById(long id);
    Optional<WorkoutLineItem> findWLIById(long id);
    //List<Workout> findByWorkoutType(WorkoutType workoutType);
}
