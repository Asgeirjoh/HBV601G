package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;

public interface AuthenticationService {
    //AuthToken authenticate(User user, String password);
    boolean isAuthenticated(User user);
}
