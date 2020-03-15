package is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public String name;
    public String description;

    @ElementCollection(targetClass = Category.class)
    @Column(name="category", nullable = false)
    @CollectionTable(name="exercise_categories", joinColumns = {@JoinColumn(name = "exercise_id")})
    public Set<Category> categories;

    public Exercise() {
    }

    public Exercise(String name, String description, HashSet<Category> categories) {
        this.name = name;
        this.description = description;
        this.categories = categories;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
