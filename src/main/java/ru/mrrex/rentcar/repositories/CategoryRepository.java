package ru.mrrex.rentcar.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrrex.rentcar.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByPublicId(UUID publicId);
}
