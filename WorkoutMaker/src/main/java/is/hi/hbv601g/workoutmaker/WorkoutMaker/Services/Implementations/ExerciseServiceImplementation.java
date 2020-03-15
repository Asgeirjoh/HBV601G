package is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.Implementations;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.Exercise;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories.ExerciseRepository;
import is.hi.hbv601g.workoutmaker.WorkoutMaker.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImplementation implements ExerciseService {

    ExerciseRepository repository;

    @Autowired
    public ExerciseServiceImplementation(ExerciseRepository exerciseRepository) {
        this.repository = exerciseRepository;
    }

    @Override
    public Exercise save(Exercise exercise) { return repository.save(exercise); }

    @Override
    public void delete(Exercise exercise) { repository.delete(exercise); }

    @Override
    public void deleteAll() { repository.deleteAll(); }

    @Override
    public boolean existsById(long id) { return repository.existsById(id); }

    @Override
    public boolean existsByName(String name) { return repository.existsByName(name); }

    @Override
    public Optional<Exercise> findById(long id) { return repository.findById(id); }

    @Override
    public List<Exercise> findByName(String name) { return repository.findByName(name); }

    @Override
    public List<Exercise> findAll() { return repository.findAll(); }
}
