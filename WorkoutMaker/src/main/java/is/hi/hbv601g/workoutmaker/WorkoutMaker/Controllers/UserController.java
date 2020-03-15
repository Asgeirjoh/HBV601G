package is.hi.hbv601g.workoutmaker.WorkoutMaker.Controllers;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Security.JwtTokenUtil;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Security.Model.JwtRequest;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Security.Model.JwtResponse;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.Implementations.JwtUserDetailsService;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if(userService.findByUsername(user.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    /*
    * Returns: List<User>
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> usersGET(){
        return userService.findAll();
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
    public User loggedinGET(Authentication authentication){
        return userService.findByUsername(authentication.getName());
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
