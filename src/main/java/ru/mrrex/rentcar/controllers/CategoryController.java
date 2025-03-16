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
import ru.mrrex.rentcar.dto.mappers.CarMapper;
import ru.mrrex.rentcar.dto.mappers.CategoryMapper;
import ru.mrrex.rentcar.dto.responses.CarResponse;
import ru.mrrex.rentcar.dto.responses.CategoryResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.models.Car;
import ru.mrrex.rentcar.models.Category;
import ru.mrrex.rentcar.services.CarService;
import ru.mrrex.rentcar.services.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category Controller",
        description = "Operations related to rental car categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CarService carService;

    private final CategoryMapper categoryMapper;
    private final CarMapper carMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Get all categories", description = "Returns the list of all car categories")
    @ApiResponse(responseCode = "200", description = "Categories list returned")
    @ApiResponse(responseCode = "400", description = "Incorrect pagination or sorting")
    public List<CategoryResponse> getCategories(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "size",
                    defaultValue = ""
                            + PaginationConstants.DEFAULT_CATEGORIES_PER_PAGE) int pageSize,
            @RequestParam(name = "sort", defaultValue = "asc") String rawSortDirection) {
        if (pageNumber < 0)
            throw new ApplicationError(HttpStatus.BAD_REQUEST, "Page number must be at least 0");

        if (pageSize < PaginationConstants.MIN_CATEGORIES_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size must be at least " + PaginationConstants.MIN_CATEGORIES_PER_PAGE);

        if (pageSize > PaginationConstants.MAX_CATEGORIES_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size cannot exceed " + PaginationConstants.MAX_CATEGORIES_PER_PAGE);

        Direction sortDirection = Direction.fromOptionalString(rawSortDirection)
                .orElseThrow(() -> new ApplicationError(HttpStatus.BAD_REQUEST,
                        "The sorting direction can be either ASC or DESC only"));

        Sort sort = Sort.by(sortDirection, "name");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        List<Category> categories = categoryService.getCategories(pageable);

        return categoryMapper.toCategoryResponseList(categories);
    }

    @GetMapping("/{publicId}")
    @Operation(summary = "Get category by public id", description = "Returns the category by public id")
    @ApiResponse(responseCode = "200", description = "Category returned")
    @ApiResponse(responseCode = "404", description = "Category not found")
    public CategoryResponse getCategory(@PathVariable UUID publicId) {
        Category category = categoryService.getCategory(publicId).orElseThrow(
                () -> new ApplicationError(HttpStatus.NOT_FOUND, "Category not found"));

        return categoryMapper.toCategoryResponse(category);
    }

    @GetMapping("/{publicId}/cars")
    @Operation(summary = "Get cars by category", description = "Returns a list of cars of a specific category")
    @ApiResponse(responseCode = "200", description = "List of cars returned")
    @ApiResponse(responseCode = "400", description = "Incorrect pagination or sorting")
    @ApiResponse(responseCode = "404", description = "Category not found")
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

        List<Car> cars = categoryService.getCategory(publicId)
                .map(category -> carService.getCarsByCategory(category, pageable))
                .orElseThrow(() -> new ApplicationError(HttpStatus.NOT_FOUND, "Category not found"));

        return carMapper.toCarResponseList(cars);
    }
}
