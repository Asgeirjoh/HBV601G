package is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String workoutName;
    private String description;


    private String workoutTypes;

    @ManyToOne//(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<WorkoutLineItem> exercises = new ArrayList<>();

    public Workout() {
    }


    public Workout(User user, String workoutName, String description, String workoutTypes) {
        this.user = user;
        this.workoutName = workoutName;
        this.description = description;
        this.workoutTypes = workoutTypes;
    }

    //alt + insert fyrir getter and setter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWorkoutTypes() {
        return workoutTypes;
    }

    public void setWorkoutTypes(String workoutTypes) {
        this.workoutTypes = workoutTypes;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setExercises(List<WorkoutLineItem> exercises) {
        this.exercises = exercises;
    }

    public List<WorkoutLineItem> getExercises() {
        return this.exercises;
    }

    @Override
    public String toString() {
        return this.workoutName;
    }
}
