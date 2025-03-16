package ru.mrrex.rentcar.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.constants.PaginationConstants;
import ru.mrrex.rentcar.dto.mappers.CarMapper;
import ru.mrrex.rentcar.dto.mappers.DetailedCarMapper;
import ru.mrrex.rentcar.dto.responses.CarResponse;
import ru.mrrex.rentcar.dto.responses.DetailedCarResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.models.Car;
import ru.mrrex.rentcar.services.CarService;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;
    private final DetailedCarMapper detailedCarMapper;

    @GetMapping
    public List<CarResponse> getCars(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "size",
                    defaultValue = "" + PaginationConstants.DEFAULT_CARS_PER_PAGE) int pageSize,
            @RequestParam(name = "sort", defaultValue = "asc") String rawSortDirection) {
        if (pageNumber < 0)
            throw new ApplicationError(HttpStatus.BAD_REQUEST, "Page number must be at least 0");

        if (pageSize < PaginationConstants.MIN_CARS_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size must be at least " + PaginationConstants.MIN_CARS_PER_PAGE);

        if (pageSize > PaginationConstants.MAX_CARS_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size cannot exceed " + PaginationConstants.MAX_CARS_PER_PAGE);

        Direction sortDirection = Direction.fromOptionalString(rawSortDirection)
                .orElseThrow(() -> new ApplicationError(HttpStatus.BAD_REQUEST,
                        "The sorting direction can be either ASC or DESC only"));

        Sort sort = Sort.by(sortDirection, "name");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        List<Car> cars = carService.getCars(pageable);
        
        return carMapper.toCarResponseList(cars);
    }

    @GetMapping("/popular")
    public List<CarResponse> getPopularCars() {
        Sort sort = Sort.by(Direction.ASC, "name");
        Pageable pageable = PageRequest.of(0, 5, sort);

        List<Car> cars = carService.getCars(pageable);

        return carMapper.toCarResponseList(cars); 
    }

    @GetMapping("/{publicId}")
    public DetailedCarResponse getCar(@PathVariable UUID publicId) {
        Car car = carService.getCarByPublicId(publicId)
                .orElseThrow(() -> new ApplicationError(HttpStatus.NOT_FOUND, "Car not found"));

        return detailedCarMapper.toDetailedCarResponse(car);
    }
}
