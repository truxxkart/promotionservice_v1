package com.truxxkart.promotionservice_v1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Email
	@Size(max = 150)
	@Column(unique = true)
	private String email;

	@NotBlank
	@Size(max = 15)
	private String phone;

	@NotBlank
	 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String passwordHash;
	

    @Column(unique = true)
    private String gstin;
   
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PincodeAvailability> pincodeAvailabilities;


    @ElementCollection
    @CollectionTable(name = "user_promotion_usage", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "promotion_id")
    @Column(name = "usage_count")
    private Map<Long, Integer> promotionUsage = new HashMap<>();
    
    private Boolean isVerified = false;

	
	private String role = "USER";
	private String userType;    // NEW_USER,REGULAR_USER...

	private Boolean isActive = true;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	

	// Getters and Setters
}
