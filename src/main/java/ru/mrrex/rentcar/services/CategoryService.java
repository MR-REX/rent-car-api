package ru.mrrex.rentcar.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import ru.mrrex.rentcar.models.Category;

public interface CategoryService {

    List<Category> getCategories(Pageable pageable);
    Optional<Category> getCategory(UUID publicId);
}
