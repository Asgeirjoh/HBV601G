package is.hi.hbv601g.workoutmaker.WorkoutMaker.Controllers;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    //private WorkoutService workoutService;
    private AuthenticationService authenticationService;

    @Autowired
    public HomeController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    /*
    * REST: Skilar engu núna, notum ekki homeController
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void Home(){

    }
    /* á ekki við hjá okkur, homepage-ið okkar verður eiginlega bara tóm síða með link á log-in page
    @RequestMapping(value = "/addworkout", method = RequestMethod.POST)
    public String addWorkout(@Valid Workout workout, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "add-workout";
        }
        Workout savedWorkout = workoutService.saveWorkout(workout);
        model.addAttribute("workouts", workoutService.findAll());
        return "Velkominn";
    }

    //til að fá formið
    @RequestMapping(value="/addworkout", method = RequestMethod.GET)
    public String addWorkoutForm(Model model){
        return "add-workout";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public String deleteWorkout(@PathVariable("id") long id, Model model){
        Workout workout = workoutService.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid workout ID"));
        workoutService.delete(workout);
        model.addAttribute("workout", workoutService.findAll());
        return "Velkominn";
    }
    */

}
