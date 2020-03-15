package is.hi.hbv601g.workoutmaker.WorkoutMaker.Controllers;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Exercise;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Workout;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.WorkoutLineItem;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.ExerciseService;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.UserService;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.WorkoutService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WorkoutController {
    private ExerciseService exerciseService;
    private WorkoutService workoutService;
    private UserService userService;

    @Autowired
    public WorkoutController(ExerciseService exerciseService, WorkoutService workoutService, UserService userService){
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
        this.userService = userService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public List<Workout> workoutGET(Authentication authentication){
        User user = userService.findByUsername(authentication.getName());
        return workoutService.findByUser(user);
    }

    /*
    * REST: bætti bara við Authentication authentication og geri svo allt annað eins nema nú er sessionuser fengin
    * út frá tokeninu. Veit ekki hvort það sé rétt
     */
    @RequestMapping(value = "/workouts", method = RequestMethod.POST)
    public Workout addWorkout(@Valid @RequestBody Workout workout, BindingResult result, Authentication authentication) {
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid workout format");
        }
        User sessionUser = userService.findByUsername(authentication.getName());
        //ef user loggaður inn þá save-a workoutið
        if(sessionUser  != null){
            ArrayList<WorkoutLineItem> wlis = new ArrayList<>(workout.getExercises());
            ArrayList<WorkoutLineItem> wlisOut = new ArrayList<>();

            for (WorkoutLineItem l: wlis) {
                //æf æfingin er ekki lögleg þá er henni bara ekki bætt við workoutið
                if (exerciseService.existsById(l.getExId())) {
                    Exercise ex = exerciseService.findById(l.getExId()).get();
                    l.setExercise(ex);
                    l.setWorkout(workout);
                    wlisOut.add(l);
                }
            }
            workout.setExercises(wlisOut);
            workout.setUser(sessionUser);
            return workoutService.saveWorkout(workout);
        }
        return null;
    }


    @RequestMapping(value = "/workouts/{workoutId}", method = RequestMethod.PUT)
    public Workout editWorkout(@PathVariable("workoutId") long wId,@Valid @RequestBody Workout workout, BindingResult result, Authentication authentication) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid workout format");
        }

        User sessionUser = userService.findByUsername(authentication.getName());

        // hacky útfærsla á update því annars voru einhver leiðindi
        if(sessionUser  != null) {
            List<WorkoutLineItem> wlis = workout.getExercises();
            ArrayList<WorkoutLineItem> wlisOut = new ArrayList<>();
            for (WorkoutLineItem l : wlis) {
                if (exerciseService.existsById(l.getExId())) {
                    Exercise ex = exerciseService.findById(l.getExId()).get();
                    l.setExercise(ex);
                    l.setWorkout(workout);
                    wlisOut.add(l);
                }
                l.setWorkout(workout);
            }
            workout.setExercises(wlisOut);
            workout.setUser(sessionUser);
            return workoutService.updateWorkout(wId, workout);

        } else {
            return null;
        }

    }

    /*
    * Returns: Workout úr gagnagrunni
     */
    @RequestMapping(value = "/workouts/{workoutId}", method = RequestMethod.GET)
    public Workout viewWorkoutGET(@PathVariable("workoutId") int wId) {
        if (workoutService.findWorkoutById(wId).isPresent()) {
            return workoutService.findWorkoutById(wId).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid workout ID");
        }
    }

    /*
    * Eyða workoutLineItem úr gagnagrunni
     */
    @RequestMapping(value = "/workouts/exercise/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> workoutLineItemDelete(@PathVariable long id) {
        if (workoutService.findWLIById(id).isPresent()) {
            //workoutId notað til að vísa á rétt view: return "redirect:/view-workout/" + workoutId;
            //long workoutId = workoutService.findWLIById(id).get().getWorkout().getId();
            workoutService.deleteWLI(workoutService.findWLIById(id).get());
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid workoutLineItem ID");
        }
    }

    /*
    * Eyða workout úr gagnagrunni
     */
    @RequestMapping(value = "/workouts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> workoutDelete(@PathVariable long id) {
        if (workoutService.findWorkoutById(id).isPresent()) {
            Workout workout = workoutService.findWorkoutById(id).get();
            workoutService.deleteWorkout(workout);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid workout ID");
        }
    }

}
