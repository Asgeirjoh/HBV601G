package is.hi.hbv601g.workoutmaker.WorkoutMaker.Repositories;

import is.hi.hbv601g.workoutmaker.WorkoutMaker.Entities.WorkoutLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutLineItemRepository extends JpaRepository<WorkoutLineItem, Long> {
    WorkoutLineItem save(WorkoutLineItem wli);
    WorkoutLineItem saveAndFlush(WorkoutLineItem wli);
    void delete(WorkoutLineItem wli);
    void deleteAll();
    Optional<WorkoutLineItem> findById(long id);
}
