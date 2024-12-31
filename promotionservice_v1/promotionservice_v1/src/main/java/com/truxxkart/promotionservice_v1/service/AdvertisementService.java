package com.truxxkart.promotionservice_v1.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.truxxkart.promotionservice_v1.entity.Advertisement;
import com.truxxkart.promotionservice_v1.enums.AdPlacement;

public interface AdvertisementService {
	public Advertisement createAdvertisement(Advertisement dto);
	public void validateDates(LocalDateTime startDate, LocalDateTime endDate) ;
	public Advertisement findAdvertisementById(Long id);
	  public void deleteAdvertisement(Long id);
	  public void trackImpression(Long id);
	 public List<Advertisement> getActiveAdvertisements(AdPlacement placement);
	 public void trackClick(Long adId);
	 public List<Advertisement> getAllAds();
	 public List<Advertisement> getAdsByPlacement(AdPlacement placement);
	 public List<Advertisement> findAdvertisementByStatus(String field,Boolean findBy);
	 public List<Advertisement> findAdvertisementByType(String advertisementType);
	 public List<Advertisement> findByDate(String field,LocalDate date);
	 public Advertisement updateAdvertisementStatus(Long id,String field,Boolean toBeUpdated);
	 public Advertisement updateAdvertisementField(Long id,String field,String toBeUpdated);
     public Integer getClicksOrImpression(String field,Long advertisementId);
	 List<Advertisement> findByActiveFalseAndStartDateBefore(LocalDateTime now);
	 List<Advertisement> findByActiveTrueAndEndDateBefore(LocalDateTime now);
}
