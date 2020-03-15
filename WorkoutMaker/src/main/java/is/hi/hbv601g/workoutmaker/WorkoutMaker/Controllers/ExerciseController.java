package is.hi.hbv601g.workoutmaker.WorkoutMaker.Controllers;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Exercise;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseController {
    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    /*
    * REST: Skilar niðurstöðu leitarinnar
    * Param: String search
    * Returns: List<Exercise>
     */
    @RequestMapping(value="/exerciseSearch", method= RequestMethod.POST)
    public List<Exercise> searchExercise(@RequestParam(value="search", required = false) String search) {
        return exerciseService.findByName(search);
    }

    /*
    * Skilar lista af öllum æfingum
    * Return: List<Exercise>
     */
    @RequestMapping(value = "/exercises", method = RequestMethod.GET)
    public List<Exercise> exerciseGET(){
        return exerciseService.findAll();
    }
}
