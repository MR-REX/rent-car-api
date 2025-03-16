package ru.mrrex.rentcar.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
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
import org.springframework.data.domain.Pageable;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.repositories.BrandRepository;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTests {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    public void testGetBrands() {
        List<Brand> brands = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Brand brand = new Brand(i, UUID.randomUUID(), "Brand " + (i + 1), "url",
                    LocalDateTime.now(), LocalDateTime.now(), null);

            brands.add(brand);
        }

        Pageable pageable = PageRequest.of(0, 10);
        Page<Brand> brandPage = new PageImpl<>(brands, pageable, brands.size());

        when(brandRepository.findAll(pageable)).thenReturn(brandPage);

        List<Brand> result = brandService.getBrands(pageable);

        assertNotNull(result);
        assertEquals(brands.size(), result.size());
        assertEquals(brands, result);

        verify(brandRepository).findAll(pageable);
    }

    @Test
    public void testGetBrand_Success() {
        UUID publicId = UUID.randomUUID();
        Brand brand = new Brand(1, publicId, "Brand 1", "url",
                    LocalDateTime.now(), LocalDateTime.now(), null);

        when(brandRepository.findByPublicId(publicId)).thenReturn(Optional.of(brand));

        Optional<Brand> result = brandService.getBrand(publicId);

        assertTrue(result.isPresent());
        assertEquals(brand, result.get());

        verify(brandRepository).findByPublicId(publicId);
    }

    @Test
    public void testGetBrand_NotFound() {
        UUID publicId = UUID.randomUUID();
        when(brandRepository.findByPublicId(publicId)).thenReturn(Optional.empty());

        Optional<Brand> result = brandService.getBrand(publicId);

        assertFalse(result.isPresent());
        verify(brandRepository).findByPublicId(publicId);
    }
}
