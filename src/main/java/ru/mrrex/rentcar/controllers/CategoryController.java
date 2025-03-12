package ru.mrrex.rentcar.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.dto.mappers.CategoryMapper;
import ru.mrrex.rentcar.dto.responses.CategoryResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.models.Category;
import ru.mrrex.rentcar.services.CategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    public static final int MIN_CATEGORIES_PER_PAGE = 1;
    public static final int MAX_CATEGORIES_PER_PAGE = 10;
    public static final int DEFAULT_CATEGORIES_PER_PAGE = 10;

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryResponse> getCategories(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "" + DEFAULT_CATEGORIES_PER_PAGE) int size,
            @RequestParam(defaultValue = "asc") String sort) {
        if (page < 0)
            throw new ApplicationError(HttpStatus.BAD_REQUEST, "Page number must be at least 0");

        if (size < MIN_CATEGORIES_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size must be at least " + MIN_CATEGORIES_PER_PAGE);

        if (size > MAX_CATEGORIES_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Page size cannot exceed " + MAX_CATEGORIES_PER_PAGE);

        List<Category> categories =
                categoryService.getCategories(page, size, "name", Sort.Direction.fromString(sort));

        return categoryMapper.toCategoryResponseList(categories);
    }

    @GetMapping("/{publicId}")
    public CategoryResponse getCategory(@PathVariable UUID publicId) {
        Category category = categoryService.getCategory(publicId)
                .orElseThrow(() -> new ApplicationError(HttpStatus.NOT_FOUND, "Category not found"));

        return categoryMapper.toCategoryResponse(category);
    }
}
