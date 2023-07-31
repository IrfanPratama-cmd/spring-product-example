package product.springproduct.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import product.springproduct.model.Brand;
import product.springproduct.payload.ResponseHandler;
import product.springproduct.services.BrandService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class BrandController {
     private final BrandService brandService;
     
     @Autowired
  public BrandController(BrandService brandService){
    this.brandService  = brandService;
  }

  @GetMapping("/brands")
  public List<Brand> getAllBrand() {
        
      return brandService.getAllBrand();
  }

  @GetMapping("/brands/{id}")
  public ResponseEntity<Brand> getBrandById(@PathVariable UUID id) {
      Optional<Brand> Brand = brandService.getBrandById(id);
      return Brand.map(ResponseEntity::ok)
              .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/brands")
  public ResponseEntity<Brand> saveBrand(@RequestBody Brand Brand) {
      Brand savedBrand = brandService.saveBrand(Brand);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
  }

  @PutMapping("/brands/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable UUID id, @RequestBody Brand Brand) {
        Optional<Brand> existingBrand = brandService.getBrandById(id);
        if (existingBrand.isPresent()) {
            Brand updatedBrand = existingBrand.get();
            updatedBrand.setBrandName(Brand.getBrandName());
            updatedBrand.setDescription(Brand.getDescription());
            return ResponseEntity.ok(brandService.saveBrand(updatedBrand));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Object> deleteBrand(@PathVariable UUID id) {
        Optional<Brand> Brand = brandService.getBrandById(id);
        if (Brand.isPresent()) {
            brandService.deleteBrand(id);
            return ResponseHandler.generateResponse("Deleted Succesfull!", HttpStatus.OK, Brand);
        } else {
            return ResponseHandler.generateResponse("id not found", HttpStatus.MULTI_STATUS, null);
        }
    }
}
