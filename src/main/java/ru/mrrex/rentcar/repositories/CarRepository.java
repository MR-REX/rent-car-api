package ru.mrrex.rentcar.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.models.Car;
import java.util.List;
import java.util.Optional;
import ru.mrrex.rentcar.models.Category;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, Long> {

    @EntityGraph(attributePaths = {"brand", "categories"})
    Page<Car> findAll(Pageable pageable);

    Optional<Car> findWithCategoriesByPublicId(UUID publicId);
    List<Car> findByBrand(Brand brand, Pageable pageable);
    List<Car> findByCategories(List<Category> categories, Pageable pageable);
}
