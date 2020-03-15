package is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities;

import javax.persistence.*;

@Entity
public class WorkoutLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Workout workout;

    @ManyToOne//(cascade = CascadeType.ALL)
    private Exercise exercise;

    private int exId;
    private int sets;
    private int reps;

    public WorkoutLineItem(){
    }

    public WorkoutLineItem(Workout workout, int sets, int reps, Exercise exercise, int exId) {
        this.workout = workout;
        this.sets = sets;
        this.reps = reps;
        this.exercise = exercise;
        this.exId = exId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getExId() {
        return exId;
    }

    public void setExId(int exId) {
        this.exId = exId;
    }
}
