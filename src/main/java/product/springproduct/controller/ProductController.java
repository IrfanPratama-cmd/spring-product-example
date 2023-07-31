package product.springproduct.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import product.springproduct.model.Product;
import product.springproduct.payload.ResponseHandler;
import product.springproduct.services.ProductService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ProductController {
     private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService){
    this.productService  = productService;
  }

  @GetMapping("/products")
  public Page<Product> searchProductsWithPagination(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productService.getFilteredAndPaginatedProduct(keyword, pageRequest);
    }

  @GetMapping("/products/search")
    public List<Product> searchProducts(
            @RequestParam("keyword") String keyword) {
        return productService.getFilterProduct(keyword);
    }

//  @GetMapping("/products")
//   public List<Product> getAllProduct() {
        
//       return productService.getAllProduct();
//   }


  @GetMapping("/products/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
      Optional<Product> product = productService.getProductById(id);
      return product.map(ResponseEntity::ok)
              .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/products")
  public ResponseEntity<Product> saveProduct(@RequestBody Product Product) {
      Product savedProduct = productService.saveProduct(Product);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
  }

  @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product Product) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            Product updatedProduct = existingProduct.get();
            updatedProduct.setProductName(Product.getProductName());
            updatedProduct.setProductCode(Product.getProductCode());
            updatedProduct.setDescription(Product.getDescription());
            updatedProduct.setCategory(Product.getCategory());
            updatedProduct.setBrand(Product.getBrand());
            updatedProduct.setPrice(Product.getPrice());
            updatedProduct.setStock(Product.getStock());
            return ResponseEntity.ok(productService.saveProduct(updatedProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        Optional<Product> Product = productService.getProductById(id);
        if (Product.isPresent()) {
            productService.deleteProduct(id);
            return ResponseHandler.generateResponse("Deleted Succesfull!", HttpStatus.OK, Product);
        } else {
            return ResponseHandler.generateResponse("id not found", HttpStatus.MULTI_STATUS, null);
        }
    }
}
