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
import ru.mrrex.rentcar.dto.mappers.BrandMapper;
import ru.mrrex.rentcar.dto.responses.BrandResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.services.BrandService;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private static final int MAX_BRANDS_PER_PAGE = 10;

    private final BrandService brandService;
    private final BrandMapper brandMapper;

    @GetMapping
    public List<BrandResponse> getBrands(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort) {
        if (page < 0)
            throw new ApplicationError(HttpStatus.BAD_REQUEST, "Page number must be at least 0");

        if (size < 1)
            throw new ApplicationError(HttpStatus.BAD_REQUEST, "Page size must be at least 1");

        if (size > MAX_BRANDS_PER_PAGE)
            throw new ApplicationError(HttpStatus.BAD_REQUEST,
                    "Size cannot exceed " + MAX_BRANDS_PER_PAGE);

        List<Brand> brands =
                brandService.getBrands(page, size, "name", Sort.Direction.fromString(sort));

        return brandMapper.toBrandResponseList(brands);
    }

    @GetMapping("/{publicId}")
    public BrandResponse getBrand(@PathVariable UUID publicId) {
        Brand brand = brandService.getBrand(publicId)
                .orElseThrow(() -> new ApplicationError(HttpStatus.NOT_FOUND, "Brand not found"));

        return brandMapper.toBrandResponse(brand);
    }
}
