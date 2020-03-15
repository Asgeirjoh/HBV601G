package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.Implementations;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories.UserRepository;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isAuthenticated(User user) {
        return true;
    }

}
