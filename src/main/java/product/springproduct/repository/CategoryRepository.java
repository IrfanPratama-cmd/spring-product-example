package product.springproduct.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import product.springproduct.model.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
  List<Category> findByName(String name);
}
