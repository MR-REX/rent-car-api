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
import ru.mrrex.rentcar.constants.PaginationConstants;
import ru.mrrex.rentcar.models.Category;
import ru.mrrex.rentcar.repositories.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void testGetCategories() {
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < PaginationConstants.MAX_CATEGORIES_PER_PAGE; i++) {
            Category category = new Category();
            category.setPublicId(UUID.randomUUID());
            category.setName("Category " + (i + 1));

            categories.add(category);
        }

        PageRequest pageable = PageRequest.of(0, PaginationConstants.MAX_CATEGORIES_PER_PAGE);
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        List<Category> result = categoryService.getCategories(pageable);

        assertNotNull(result);
        assertEquals(categories.size(), result.size());
        assertEquals(categories, result);

        verify(categoryRepository).findAll(pageable);
    }

    @Test
    public void testGetCategory_Success() {
        UUID publicId = UUID.randomUUID();

        Category category = new Category();
        category.setPublicId(publicId);
        
        when(categoryRepository.findByPublicId(publicId)).thenReturn(Optional.of(category));
        Optional<Category> result = categoryService.getCategory(publicId);

        assertTrue(result.isPresent());
        assertEquals(category, result.get());

        verify(categoryRepository).findByPublicId(publicId);
    }

    @Test
    public void testGetCategory_NotFound() {
        UUID publicId = UUID.randomUUID();
        
        when(categoryRepository.findByPublicId(publicId)).thenReturn(Optional.empty());
        Optional<Category> result = categoryService.getCategory(publicId);

        assertFalse(result.isPresent());
        verify(categoryRepository).findByPublicId(publicId);
    }
}
