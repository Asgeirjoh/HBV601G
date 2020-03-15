package is.hi.hbv601g.workoutmaker.WorkoutMaker.Controllers;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Exercise;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExerciseController {
    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @RequestMapping(value="/search")
    public String search() {
        return "search";
    }

    @RequestMapping(value="/exerciseSearch", method= RequestMethod.POST)
    public String searchExercise(@RequestParam(value="search", required = false) String search, Model model) {
        System.out.println("The search object"+search);
        List<Exercise> exercise = exerciseService.findByName(search);
        model.addAttribute("exercise", exercise);
        return "search"; //á að skila síðunni sem sýnir niðurstöðu leitarinnar
    }

    @RequestMapping(value = "/exercises", method = RequestMethod.GET)
    public String exerciseGET(Model model){
        model.addAttribute("exercises", exerciseService.findAll());
        return "exercises";
    }
}
