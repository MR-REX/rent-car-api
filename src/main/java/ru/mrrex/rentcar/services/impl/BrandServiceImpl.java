package ru.mrrex.rentcar.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.models.Brand;
import ru.mrrex.rentcar.repositories.BrandRepository;
import ru.mrrex.rentcar.services.BrandService;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> getBrands(Pageable pageable) {
        return brandRepository.findAll(pageable).getContent();
    }

    @Override
    public Optional<Brand> getBrand(UUID publicId) {
        return brandRepository.findByPublicId(publicId);
    }
}
