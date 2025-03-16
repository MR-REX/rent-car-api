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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.constants.PaginationConstants;
import ru.mrrex.rentcar.dto.mappers.BrandMapper;
import ru.mrrex.rentcar.dto.mappers.CarMapper;
import ru.mrrex.rentcar.dto.responses.BrandResponse;
import ru.mrrex.rentcar.dto.responses.CarResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.models.Car;
import ru.mrrex.rentcar.services.BrandService;
import ru.mrrex.rentcar.services.CarService;

@RestController
@RequestMapping("/api/v1/brands")
@Tag(name = "Brand Controller",
        description = "Operations related to brands of rented cars")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final CarService carService;

    private final BrandMapper brandMapper;
    private final CarMapper carMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Get all brands", description = "Returns the list of all car brands")
    @ApiResponse(responseCode = "200", description = "Brands list returned")
    @ApiResponse(responseCode = "400", description = "Incorrect pagination or sorting")
    public List<BrandResponse> getBrands(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "size",
                    defaultValue = "" + PaginationConstants.DEFAULT_BRANDS_PER_PAGE) int pageSize,
            @RequestParam(name = "sort", defaultValue = "asc") String rawSortDirection) {
        if (pageNumber < 0)
            throw new ApplicationError(HttpStatus.BAD_REQUEST, "Page number must be at least 0");

        if (pageSize < PaginationConstants.MIN_BRANDS_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size must be at least " + PaginationConstants.MIN_BRANDS_PER_PAGE);

        if (pageSize > PaginationConstants.MAX_BRANDS_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size cannot exceed " + PaginationConstants.MAX_BRANDS_PER_PAGE);

        Direction sortDirection = Direction.fromOptionalString(rawSortDirection)
                .orElseThrow(() -> new ApplicationError(HttpStatus.BAD_REQUEST,
                        "The sorting direction can be either ASC or DESC only"));

        Sort sort = Sort.by(sortDirection, "name");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        List<Brand> brands = brandService.getBrands(pageable);
        
        return brandMapper.toBrandResponseList(brands);
    }

    @GetMapping("/{publicId}")
    @Operation(summary = "Get brand by public id", description = "Returns the car brand by public id")
    @ApiResponse(responseCode = "200", description = "Car brand returned")
    @ApiResponse(responseCode = "404", description = "Car brand not found")
    public BrandResponse getBrand(@PathVariable UUID publicId) {
        Brand brand = brandService.getBrand(publicId)
                .orElseThrow(() -> new ApplicationError(HttpStatus.NOT_FOUND, "Brand not found"));

        return brandMapper.toBrandResponse(brand);
    }

    @GetMapping("/{publicId}/cars")
    @Operation(summary = "Get cars of brand", description = "Returns a list of cars of a specific brand")
    @ApiResponse(responseCode = "200", description = "List of cars returned")
    @ApiResponse(responseCode = "400", description = "Incorrect pagination or sorting")
    @ApiResponse(responseCode = "404", description = "Car brand not found")
    public List<CarResponse> getBrandCars(@PathVariable UUID publicId,
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

        List<Car> cars = brandService.getBrand(publicId)
                .map(brand -> carService.getCarsByBrand(brand, pageable))
                .orElseThrow(() -> new ApplicationError(HttpStatus.NOT_FOUND, "Brand not found"));

        return carMapper.toCarResponseList(cars);
    }
}
