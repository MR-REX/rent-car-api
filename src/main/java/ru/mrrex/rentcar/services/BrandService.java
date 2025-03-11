package ru.mrrex.rentcar.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import ru.mrrex.rentcar.models.Brand;

public interface BrandService {

    List<Brand> getBrands(int pageNumber, int pageSize, String sortBy, Sort.Direction sortDirection);
    Optional<Brand> getBrand(UUID publicId);
}
