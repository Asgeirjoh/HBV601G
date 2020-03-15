package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.Implementations;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.User;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories.UserRepository;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    UserRepository repository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository){
        this.repository = userRepository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User login(User user) {
        User exists = findByUsername(user.getUsername());
        if(exists != null) {
            if(exists.getPassword().equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
