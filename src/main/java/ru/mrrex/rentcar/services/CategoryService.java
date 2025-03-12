package ru.mrrex.rentcar.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import ru.mrrex.rentcar.models.Category;

public interface CategoryService {

    List<Category> getCategories(int pageNumber, int pageSize, String sortBy, Sort.Direction sortDirection);
    Optional<Category> getCategory(UUID publicId);
}
