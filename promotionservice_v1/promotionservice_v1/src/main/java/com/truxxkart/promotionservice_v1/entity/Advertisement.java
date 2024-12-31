package com.truxxkart.promotionservice_v1.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.truxxkart.promotionservice_v1.enums.AdPlacement;
@Data
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String advertisementType;  //banner, video, text, social media post, etc
    private String title;      
    private String imageUrl;
    private String redirectUrl;

    @Enumerated(EnumType.STRING)
    private AdPlacement placement;    //homepage,cart,product etc

    private Integer clicks = 0;
    private Integer impressions = 0;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Boolean active=false;
    private Boolean isVerified=false;

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
	 @PostLoad
	    public void updateActiveStatus() {
	        LocalDateTime now = LocalDateTime.now();
	        this.active = (startDate.isBefore(now) || startDate.isEqual(now)) &&
	                      (endDate.isAfter(now) || endDate.isEqual(now));
	    }
}
