package ru.mrrex.rentcar.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrrex.rentcar.models.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByPublicId(UUID publicId);
}
