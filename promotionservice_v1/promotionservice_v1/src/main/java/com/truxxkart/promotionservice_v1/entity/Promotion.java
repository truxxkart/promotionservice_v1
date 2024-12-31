package com.truxxkart.promotionservice_v1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import java.util.Set;

import com.truxxkart.promotionservice_v1.enums.PromotionType;
@Data
@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long creatorId;
    @NotBlank
    private String creatorRole;
    @NotBlank
    private String title;     // •	Festive sales, flash sales, or anniversary campaigns.
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PromotionType type;   // e.g., PERCENTAGE_DISCOUNT, FLAT_DISCOUNT,Free Shipping,BOGO

    private Double discountValue; // Percentage or fixed amount

    private Double minPurchaseAmount; // For rule validation

    @NotBlank
    @Column(unique = true)
    private String promoCode;   

    @NotNull
    private Integer maxUsageOfPromo;
    
    private Integer currentUsageOfPromo;

  @ElementCollection
    private Set<String> eligibleUsersType;  //new user,cont user,buy unit
    
 @ElementCollection
    private Set<Long> applicableProducts;   // ex for BOGO all eligible products could be here 

     @NotNull
    private LocalDateTime startDate;
     @NotNull
    private LocalDateTime endDate;

    private Boolean isActive=false;
    private Boolean isVerified;
    private Boolean isGlobal;   //can be created by admin
    
    private Integer maxGlobalCount;   //set by admin for its max use
    private Integer currentGlobalUsageOfPromo;
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
	public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return isActive && (startDate == null || !now.isBefore(startDate)) && (endDate == null || !now.isAfter(endDate));
    }

//    // Utility method to check if a user type is eligible
//    public boolean isUserTypeEligible(String userType) {
//        return eligibleUsersType == null || eligibleUsersType.isEmpty() || eligibleUsersType.contains(userType);
//    }

    // Utility method to check if a product is applicable
    public boolean isProductApplicable(Long productId) {
        return applicableProducts == null || applicableProducts.isEmpty() || applicableProducts.contains(productId);
    }
 
}
