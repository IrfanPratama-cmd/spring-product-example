package product.springproduct.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	// @CreatedDate
    // @JoinColumn(name = "created_at", nullable = false, updatable = false)
    // private Date createdAt;

    // @LastModifiedDate
    // @JoinColumn(name = "updated_at")

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

	private Date createdAt;

    private Date updatedAt;

	private String name;

	private String productCode;

	private Double price;

	private int stock;

	private String description;

	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

    public Product(){
        
    }

	public Product(String name, String description, String productCode, Double price, int stock, Category category, Brand brand) {
		this.name = name;
		this.description = description;
		this.productCode = productCode;
		this.price = price;
		this.stock = stock;
		this.brand = brand;
		this.category = category;
	}

    public UUID getId() {
		return id;
	}

	public Brand getBrand(){
		return brand;
	}

	public Category getCategory(){
		return category;
	}

	public void setBrand(Brand brand){
		this.brand = brand;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public String getProductName() {
		return name;
	}

    public String getProductCode() {
		return productCode;
	}

    public Double getPrice() {
		return price;
	}

    public int getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Date getCreatedAt(){
        return createdAt;
    }

    public Date getUpdateAt(){
        return updatedAt;
    }

	public void setProductName(String productName) {
		this.name = productName;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
