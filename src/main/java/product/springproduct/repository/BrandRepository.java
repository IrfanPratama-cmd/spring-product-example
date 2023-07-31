package product.springproduct.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import product.springproduct.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    List<Brand> findByName(String name);
}
