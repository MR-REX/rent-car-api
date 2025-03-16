package ru.mrrex.rentcar.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.mrrex.rentcar.constants.PaginationConstants;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.models.Car;
import ru.mrrex.rentcar.models.Category;
import ru.mrrex.rentcar.repositories.CarRepository;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTests {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void testGetCarByPublicId_Success() {
        UUID publicId = UUID.randomUUID();

        Car car = new Car();
        car.setPublicId(publicId);
        car.setName("Tesla Model X");

        when(carRepository.findWithCategoriesByPublicId(publicId)).thenReturn(Optional.of(car));
        Optional<Car> result = carService.getCarByPublicId(publicId);

        assertTrue(result.isPresent());
        assertEquals(car, result.get());

        verify(carRepository).findWithCategoriesByPublicId(publicId);
    }

    @Test
    public void testGetCarByPublicId_NotFound() {
        UUID publicId = UUID.randomUUID();

        when(carRepository.findWithCategoriesByPublicId(publicId)).thenReturn(Optional.empty());
        Optional<Car> result = carService.getCarByPublicId(publicId);

        assertFalse(result.isPresent());
        verify(carRepository).findWithCategoriesByPublicId(publicId);
    }

    private List<Car> generateCarsList(int size) {
        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Car car = new Car();
            car.setPublicId(UUID.randomUUID());
            car.setName("Car " + (i + 1));

            cars.add(car);
        }

        return cars;
    }

    @Test
    public void testGetCars() {
        List<Car> cars = generateCarsList(PaginationConstants.MAX_CARS_PER_PAGE);

        Pageable pageable = PageRequest.of(0, PaginationConstants.MAX_CARS_PER_PAGE);
        Page<Car> carPage = new PageImpl<>(cars, pageable, cars.size());

        when(carRepository.findAll(pageable)).thenReturn(carPage);
        List<Car> result = carService.getCars(pageable);

        assertNotNull(result);
        assertEquals(cars.size(), result.size());
        assertEquals(cars, result);

        verify(carRepository).findAll(pageable);
    }

    @Test
    public void testGetCarsByBrand() {
        Brand brand = new Brand();
        brand.setPublicId(UUID.randomUUID());
        brand.setName("Tesla");

        List<Car> cars = generateCarsList(PaginationConstants.MAX_CARS_PER_PAGE);
        Pageable pageable = PageRequest.of(0, PaginationConstants.MAX_CARS_PER_PAGE);

        when(carRepository.findByBrand(brand, pageable)).thenReturn(cars);
        List<Car> result = carService.getCarsByBrand(brand, pageable);

        assertNotNull(result);
        assertEquals(cars.size(), result.size());
        assertEquals(cars, result);
        
        verify(carRepository).findByBrand(brand, pageable);
    }

    @Test
    public void testGetCarsByCategory() {
        Category category = new Category();
        category.setPublicId(UUID.randomUUID());
        category.setName("City Car");

        List<Car> cars = generateCarsList(PaginationConstants.MAX_CARS_PER_PAGE);
        Pageable pageable = PageRequest.of(0, PaginationConstants.MAX_CARS_PER_PAGE);

        when(carRepository.findByCategories(List.of(category), pageable)).thenReturn(cars);
        List<Car> result = carService.getCarsByCategory(category, pageable);

        assertNotNull(result);
        assertEquals(cars.size(), result.size());
        assertEquals(cars, result);
        
        verify(carRepository).findByCategories(List.of(category), pageable);
    }
}
