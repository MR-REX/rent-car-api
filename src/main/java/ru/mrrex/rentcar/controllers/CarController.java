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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Car Controller",
        description = "Operations related to rental cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;
    private final DetailedCarMapper detailedCarMapper;

    @GetMapping
    @Operation(summary = "Get all cars", description = "Returns a list of all rent cars")
    @ApiResponse(responseCode = "200", description = "List of cars returned")
    @ApiResponse(responseCode = "400", description = "Incorrect pagination or sorting")
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
    @Operation(summary = "Get popular cars", description = "Returns a list of popular cars")
    @ApiResponse(responseCode = "200", description = "List of cars returned")
    public List<CarResponse> getPopularCars() {
        Sort sort = Sort.by(Direction.ASC, "name");
        Pageable pageable = PageRequest.of(0, 5, sort);

        List<Car> cars = carService.getCars(pageable);

        return carMapper.toCarResponseList(cars); 
    }

    @GetMapping("/{publicId}")
    @Operation(summary = "Get car by public id", description = "Returns a detailed car info by public id")
    @ApiResponse(responseCode = "200", description = "Car details returned")
    @ApiResponse(responseCode = "404", description = "Car not found")
    public DetailedCarResponse getCar(@PathVariable UUID publicId) {
        Car car = carService.getCarByPublicId(publicId)
                .orElseThrow(() -> new ApplicationError(HttpStatus.NOT_FOUND, "Car not found"));

        return detailedCarMapper.toDetailedCarResponse(car);
    }
}
