package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.Implementations;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Workout;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.WorkoutLineItem;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories.WorkoutLineItemRepository;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories.WorkoutRepository;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//(högni freyr gunnarson, þorsteinn oskarsson, sunneva þorsteinsdóttir, helga þöll guðjónsdóttir)22 reviewar okkur og við reviewum 30
//30 = (sigursteinn pálsson, anton h. heimdal, tómas tryggvason, una rúnarsdóttirí )
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutServiceImplementation implements WorkoutService {

    WorkoutRepository workoutRepository;
    WorkoutLineItemRepository workoutLineItemRepository;

    @Autowired
    public WorkoutServiceImplementation(WorkoutRepository workoutRepository, WorkoutLineItemRepository wliRepository){
        this.workoutRepository = workoutRepository;
        this.workoutLineItemRepository = wliRepository;
    }

    @Override
    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Workout updateWorkout(long wId, Workout workout) {
        Workout old = workoutRepository.findById(wId).get();
        List<WorkoutLineItem> newWlis = workout.getExercises();
        //        old.setExercises(workout.getExercises());
        old.setDescription(workout.getDescription());
        old.setWorkoutName(workout.getWorkoutName());
        old.setWorkoutTypes(workout.getWorkoutTypes());

        for (int i = 0; i < newWlis.size(); i++) {
            WorkoutLineItem wOld = old.getExercises().get(i);
            WorkoutLineItem wNew = newWlis.get(i);
            wOld.setExercise(wNew.getExercise());
            wOld.setReps(wNew.getReps());
            wOld.setSets(wNew.getSets());
        }

        workoutRepository.save(old);

//        Workout old2 = workoutRepository.findById(wId).get();
  //      workoutRepository.delete(old2);
        return null;
    }

    @Override
    public Workout saveAndFlushWorkout(Workout workout) {
        return workoutRepository.saveAndFlush(workout);
    }

    @Override
    public WorkoutLineItem saveWLI(WorkoutLineItem wli) {
        return workoutLineItemRepository.save(wli);
    }

    @Override
    public WorkoutLineItem saveAndFlushWLI(WorkoutLineItem wli) {
        return workoutLineItemRepository.saveAndFlush(wli);
    }

    @Override
    public void deleteWorkout(Workout workout) {
        workoutRepository.delete(workout);
    }

    @Override
    public void deleteWLI(WorkoutLineItem wli) {workoutLineItemRepository.delete(wli);}

    @Override
    public void deleteAllWorkouts() {workoutRepository.deleteAll();}

    @Override
    public void deleteAllWLI() {workoutLineItemRepository.deleteAll();}

    @Override
    public Workout addToWorkout(long workoutId, WorkoutLineItem wli) {
        Workout workout = workoutRepository.findById(workoutId).get();
        List<WorkoutLineItem> exerciseList = workout.getExercises();
        exerciseList.add(wli);
        workout.setExercises(exerciseList);
        Workout updatedWorkout = saveWorkout(workout);
        return updatedWorkout;
    }

    @Override
    public Workout rmFromWorkout(long workoutId, int lineNumber) {
        Workout workout = workoutRepository.findById(workoutId).get();
        List<WorkoutLineItem> exerciseList = workout.getExercises();
        exerciseList.remove(lineNumber);
        Workout updatedWorkout = saveWorkout(workout);
        return updatedWorkout;
    }

    @Override
    public WorkoutLineItem setExerciseReps(WorkoutLineItem wli, int reps) {
        wli.setReps(reps);
        WorkoutLineItem updatedWLI = saveWLI(wli);
        return updatedWLI;
    }

    @Override
    public WorkoutLineItem setExerciseSets(WorkoutLineItem wli, int sets) {
        wli.setSets(sets);
        WorkoutLineItem updatedWLI = saveWLI(wli);
        return updatedWLI;
    }

    @Override
    public List<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @Override
    public List<Workout> findByWorkoutName(String workoutName) {
        return workoutRepository.findByWorkoutName(workoutName);
    }

    @Override
    public List<Workout> findByUser(User user) {
        List<Workout> workouts = workoutRepository.findAll();
        String sessionUsername = user.getUsername();
        List<Workout> sessionWorkouts = new ArrayList<>();
        for (Workout workout : workouts) {
            User temp = workout.getUser();
            if (temp.getUsername().equals(sessionUsername)) {
                sessionWorkouts.add(workout);
            }
        }
        return sessionWorkouts;
    }

    @Override
    public Optional<Workout> findWorkoutById(long id) {
        return workoutRepository.findById(id);
    }

    @Override
    public Optional<WorkoutLineItem> findWLIById(long id) { return workoutLineItemRepository.findById(id); }

    //@Override
    //public List<Workout> findByWorkoutType(WorkoutType workoutType) {
    //    return workoutRepository.findByWorkoutType(workoutType);
    //}
}
