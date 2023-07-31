package product.springproduct.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import product.springproduct.model.Brand;
import product.springproduct.repository.BrandRepository;

@Service
public class BrandService {
    private final BrandRepository brandRepository;

     @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(UUID id) {
        return brandRepository.findById(id);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrand(UUID id) {
        brandRepository.deleteById(id);
    }
}
