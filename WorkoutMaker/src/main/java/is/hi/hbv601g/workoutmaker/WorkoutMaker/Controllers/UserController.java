package is.hi.hbv601g.workoutmaker.WorkoutMaker.Controllers;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUpGET(User user){
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUpPOST(@Valid User user, BindingResult result, Model model){

        if(result.hasErrors()){
            return "signup";
        }
        if(user.getUsername() == "" || user.getPassword() == "") {
            model.addAttribute("message", "Please fill in username and password");
            return "signup";
        }
        User exists = userService.findByUsername(user.getUsername());
        if(exists == null){
            userService.save(user);
        }
        else {
            model.addAttribute("message", "Username already in use, please select a different username");
            return "signup";
        }
        //model.addAttribute("exercises", exerciseService.findAll());
        return "redirect:/login"; //spurning hverju þetta ætti að returna
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String usersGET(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(User user){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(@Valid User user, BindingResult result, Model model, HttpSession session){

        if(result.hasErrors()){
            return "login";
        }
        //checkar ef annaðhvort inputið er tómt //virkar ekki ef bæði
        if(user.getUsername() == "" || user.getPassword() == "") {
            model.addAttribute("message", "Please fill in username and password ");
            return "login";
        }

        //model.addAttribute("movies",movieService.findAll());
        User valid = userService.login(user);
        if(valid != null){
            session.setAttribute("LoggedInUser", user);
            return "redirect:/profile";
        }
        User exists = userService.findByUsername(user.getUsername());
        //checkar ef usernamið er á skrá
        if(exists == null) {
            model.addAttribute("message","User not found please retype or create a new user");
            model.addAttribute("signUpLink", "here");
            return "login";
        }
        model.addAttribute("message","Invalid password please try again");
        return "login";
    }

    @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    public String loggedinGET(HttpSession session, Model model){
        //model.addAttribute("movies",movieService.findAll());
        User sessionUser = (User) session.getAttribute("LoggedInUser");
        if(sessionUser  != null){
            model.addAttribute("loggedinuser", sessionUser);
            return "loggedInUser";
        }
        return "redirect:/login";
    }
}
