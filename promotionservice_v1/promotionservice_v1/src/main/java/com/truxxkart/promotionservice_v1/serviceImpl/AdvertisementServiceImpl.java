package com.truxxkart.promotionservice_v1.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truxxkart.promotionservice_v1.entity.Advertisement;
import com.truxxkart.promotionservice_v1.enums.AdPlacement;
import com.truxxkart.promotionservice_v1.repository.AdvertisementRepository;
import com.truxxkart.promotionservice_v1.service.AdvertisementService;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
	@Autowired
	private AdvertisementRepository advertisementRepo;

	@Override
	public void trackClick(Long adId) {
		 Advertisement advertisement = findAdvertisementById(adId);
	        advertisement.setClicks(advertisement.getClicks() + 1);
	        advertisementRepo.save(advertisement);
	}

	@Override
	public Advertisement createAdvertisement(Advertisement advertisement) {
		Advertisement createdAdvertisement =advertisementRepo.save(advertisement);
		return createdAdvertisement;
	}

	@Override
	public void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }


	}

	@Override
	public Advertisement findAdvertisementById(Long id) {
		 return advertisementRepo.findById(id).get();
	}

	@Override
	public void deleteAdvertisement(Long id) {
		  if (!advertisementRepo.existsById(id)) {
			  throw new IllegalArgumentException("Advertisement not found.");
	        }
	        advertisementRepo.deleteById(id);

	}

	@Override
	public void trackImpression(Long id) {
		  Advertisement advertisement = findAdvertisementById(id);
	        advertisement.setImpressions(advertisement.getImpressions() + 1);
	        advertisementRepo.save(advertisement);
	}

	@Override
	public List<Advertisement> getActiveAdvertisements(AdPlacement placement) {
		  List<Advertisement> ads;
	        if (placement != null) {
	            ads = advertisementRepo.findByPlacementAndActiveTrue(placement);
	        } else {
	            ads = advertisementRepo.findByActiveTrue();
	        }
	        return ads;
	}

}
