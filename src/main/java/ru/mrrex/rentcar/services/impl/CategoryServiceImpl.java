package ru.mrrex.rentcar.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.models.Category;
import ru.mrrex.rentcar.repositories.CategoryRepository;
import ru.mrrex.rentcar.services.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<Category> getCategory(UUID publicId) {
        return categoryRepository.findByPublicId(publicId);
    }
}
