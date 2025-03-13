package ru.mrrex.rentcar.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.models.Car;
import ru.mrrex.rentcar.models.Category;

public interface CarService {

    Optional<Car> getCarByPublicId(UUID publicId);

    List<Car> getCars(Pageable pageable);
    List<Car> getCarsByBrand(Brand brand, Pageable pageable);
    List<Car> getCarsByCategory(Category category, Pageable pageable);
}
