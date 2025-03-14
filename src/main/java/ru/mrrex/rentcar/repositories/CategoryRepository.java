package ru.mrrex.rentcar.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrrex.rentcar.models.Category;
import ru.mrrex.rentcar.models.Car;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByPublicId(UUID publicId);
    List<Category> findByCars(List<Car> cars);
}
