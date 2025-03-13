package ru.mrrex.rentcar.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.models.Car;
import ru.mrrex.rentcar.models.Category;
import ru.mrrex.rentcar.repositories.CarRepository;
import ru.mrrex.rentcar.services.CarService;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    
    @Override
    @Cacheable(value = "cars", key = "#publicId")
    public Optional<Car> getCarByPublicId(UUID publicId) {
        return carRepository.findWithCategoriesByPublicId(publicId);
    }

    @Override
    @Cacheable(value = "cars", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public List<Car> getCars(Pageable pageable) {
        return carRepository.findAll(pageable).getContent();
    }

    @Override
    @Cacheable(value = "carsByBrand", key = "#brand.id + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public List<Car> getCarsByBrand(Brand brand, Pageable pageable) {
        return carRepository.findByBrand(brand, pageable);
    }

    @Override
    @Cacheable(value = "carsByCategory", key = "#category.id + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public List<Car> getCarsByCategory(Category category, Pageable pageable) {
        return carRepository.findByCategories(List.of(category), pageable);
    }
}
