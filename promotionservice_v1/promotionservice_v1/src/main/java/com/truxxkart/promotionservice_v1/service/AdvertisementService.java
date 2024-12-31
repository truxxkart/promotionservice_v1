package com.truxxkart.promotionservice_v1.service;

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
//	 public List<Advertisement> getAdsByPlacement(AdPlacement placement)
}
