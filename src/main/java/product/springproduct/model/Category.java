package product.springproduct.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

	@Column(name = "category_name")
	private String name;

	@Column(name = "description")
	private String description;

	@OneToMany( mappedBy = "category")
    private List<Product> product;

	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

    public Category(){
        
    }

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public String getCategoryName() {
		return name;
	}

	public Date getCreatedAt(){
        return createdAt;
    }

    public Date getUpdateAt(){
        return updatedAt;
    }

	public void setCategoryName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", category_name=" + name + ", desc=" + description + "]";
	}
}