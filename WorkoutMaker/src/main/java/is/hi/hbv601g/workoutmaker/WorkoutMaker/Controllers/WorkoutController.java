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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
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
    public String workoutGET(HttpSession session, Model model){
        User sessionUser = (User) session.getAttribute("LoggedInUser");

        if(sessionUser  != null){
            model.addAttribute("workouts", workoutService.findByUser(sessionUser));

            return "profile";
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/add-workout", method = RequestMethod.POST)
    public String addWorkout(@Valid Workout workout, HttpSession session, BindingResult result, Model model) {

        if(result.hasErrors()) { return "add-workout"; }
        if(workout.getWorkoutName() == "") {
            model.addAttribute("message", "Please fill in name of Workout");

            List<Exercise> allExercises = exerciseService.findAll();
            model.addAttribute("exercises", allExercises);

            return "add-workout";
        }

        String sessionUsername = ((User) session.getAttribute("LoggedInUser")).getUsername();
        User sessionUser = userService.findByUsername(sessionUsername);
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
            workoutService.saveWorkout(workout);
            return "redirect:/profile";
        }

        return "redirect:/";
    }

    @RequestMapping(value="/add-workout", params = {"addRow"})
    public String addRow(final Workout workout, final BindingResult bindingResult, Model model) {
        WorkoutLineItem wli = new WorkoutLineItem();
        List<WorkoutLineItem> wliList = workout.getExercises();
        wliList.add(wli);
        workout.setExercises(wliList);
        model.addAttribute("workout", workout);

        List<Exercise> allExercises = exerciseService.findAll();
        model.addAttribute("exercises", allExercises);
        return "add-workout";
    }

    @RequestMapping(value = "/add-workout", method = RequestMethod.GET)
    public String addWorkoutForm(Workout workout, Model model){

        List<WorkoutLineItem> exercises = workout.getExercises();

        List<Exercise> allExercises = exerciseService.findAll();
        model.addAttribute("exercises", allExercises);

        for (int i = 0; i <= 3; i++) {
            WorkoutLineItem wli = new WorkoutLineItem();
            wli.setWorkout(workout);
            exercises.add(wli);
        }

        workout.setExercises(exercises);
        model.addAttribute("workout", workout);

        return "add-workout"; }

    @RequestMapping(value = "/edit-workout/{workoutId}", method = RequestMethod.GET)
    public String editWorkoutFormGET(@PathVariable("workoutId") int wId, Model model){
        model.addAttribute("workout", workoutService.findWorkoutById(wId).get());
        return "edit-workout";
    }

    @RequestMapping(value = "/edit-workout/{workoutId}", method = RequestMethod.POST)
    public String editWorkoutFormPOST(@PathVariable("workoutId") long wId,@Valid Workout workout, HttpSession session, BindingResult result, Model model) {
        //óli fix plz
        String sessionUsername = ((User) session.getAttribute("LoggedInUser")).getUsername();
        User sessionUser = userService.findByUsername(sessionUsername);

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
            workoutService.updateWorkout(wId, workout);
            return "redirect:/view-workout/" + wId;
        } else {
            return "redirect:/";
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
    public ResponseEntity<?> workoutLineItemDelete(@PathVariable long id , HttpSession session) {
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
    public ResponseEntity<?> workoutDelete(@PathVariable long id, HttpSession session) {
        if (workoutService.findWorkoutById(id).isPresent()) {
            Workout workout = workoutService.findWorkoutById(id).get();
            workoutService.deleteWorkout(workout);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid workout ID");
        }
    }

}
