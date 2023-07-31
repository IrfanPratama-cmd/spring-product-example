package product.springproduct.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import product.springproduct.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByNameContainingOrCategoryNameContaining(String productName, String category,  Pageable pageable);
    List<Product> findByNameContainingOrCategoryNameContaining(String productName, String category);
}
