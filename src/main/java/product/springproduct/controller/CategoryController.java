package product.springproduct.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import product.springproduct.model.Category;
import product.springproduct.payload.ResponseHandler;
import product.springproduct.services.CategoryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class CategoryController {
  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService){
    this.categoryService  = categoryService;
  }

  @GetMapping("/categories")
  public List<Category> getAllCategory() {
        
      return categoryService.getAllCategory();
  }

  @GetMapping("/categories/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
      Optional<Category> category = categoryService.getCategoryById(id);
      return category.map(ResponseEntity::ok)
              .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/categories")
  public ResponseEntity<Category> saveCategory(@RequestBody Category Category) {
      Category savedCategory = categoryService.saveCategory(Category);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
  }

  @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody Category Category) {
        Optional<Category> existingCategory = categoryService.getCategoryById(id);
        if (existingCategory.isPresent()) {
            Category updatedCategory = existingCategory.get();
            updatedCategory.setCategoryName(Category.getCategoryName());
            updatedCategory.setDescription(Category.getDescription());
            return ResponseEntity.ok(categoryService.saveCategory(updatedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable UUID id) {
        Optional<Category> Category = categoryService.getCategoryById(id);
        if (Category.isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseHandler.generateResponse("Deleted Succesfull!", HttpStatus.OK, Category);
        } else {
            return ResponseHandler.generateResponse("id not found", HttpStatus.MULTI_STATUS, null);
        }
    }

}
