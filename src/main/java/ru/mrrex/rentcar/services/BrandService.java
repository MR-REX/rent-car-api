package ru.mrrex.rentcar.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import ru.mrrex.rentcar.models.Brand;

public interface BrandService {

    List<Brand> getBrands(Pageable pageable);
    Optional<Brand> getBrand(UUID publicId);
}
