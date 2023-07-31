package product.springproduct.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import product.springproduct.model.Product;
import product.springproduct.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getFilteredAndPaginatedProduct(String keyword, Pageable pageable){
        return productRepository.findByNameContainingOrCategoryNameContaining(keyword, keyword, pageable);
    }

    public List<Product> getFilterProduct(String keyword){
        return productRepository.findByNameContainingOrCategoryNameContaining(keyword, keyword);
    }

}
