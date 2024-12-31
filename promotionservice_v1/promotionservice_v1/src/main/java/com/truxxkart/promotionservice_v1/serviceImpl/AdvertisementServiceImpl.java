package com.truxxkart.promotionservice_v1.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public List<Advertisement> getAdsByPlacement(AdPlacement placement) {
		     
		return advertisementRepo.findByPlacement(placement);
	}

	@Override
	public List<Advertisement> findAdvertisementByStatus(String field, Boolean findBy) {
		if(field.equalsIgnoreCase("ACTIVATION") ) {
			return advertisementRepo.findAll().stream().filter(adv->adv.getActive()==findBy).collect(Collectors.toList());
		}else if(field.equalsIgnoreCase("VERIFICATION") ) {
			return advertisementRepo.findAll().stream().filter(adv->adv.getIsVerified()==findBy).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<Advertisement> findAdvertisementByType(String advertisementType) {
	
		return advertisementRepo.findByAdvertisementType(advertisementType);
	}

	@Override
	public List<Advertisement> findByDate(String field, LocalDate date) {
		if(field.equalsIgnoreCase("START") ) {
			return advertisementRepo.findAll().stream().filter(adv->adv.getStartDate().toLocalDate().equals(date)).collect(Collectors.toList());
		}else if(field.equalsIgnoreCase("END") ) {
			return advertisementRepo.findAll().stream().filter(adv->adv.getEndDate().toLocalDate().equals(date)).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public Advertisement updateAdvertisementStatus(Long id, String field, Boolean toBeUpdated) {
		Optional<Advertisement> optAdvertisement =advertisementRepo.findById(id);
		if(optAdvertisement.isPresent()) {
			Advertisement advertisement =optAdvertisement.get();
			if(field.equalsIgnoreCase("ACTIVATION") ) {
				advertisement.setActive(toBeUpdated);
			}else if(field.equalsIgnoreCase("VERIFICATION") ) {
				advertisement.setIsVerified(toBeUpdated);
			}
			return advertisementRepo.save(advertisement);
		}
		
		return null;
	}

	@Override
	public Advertisement updateAdvertisementField(Long id, String field, String toBeUpdated) {
		Optional<Advertisement> optAdvertisement =advertisementRepo.findById(id);
		if(optAdvertisement.isPresent()) {
			Advertisement advertisement =optAdvertisement.get();
			if(field.equalsIgnoreCase("TITLE") ) {
				advertisement.setTitle(toBeUpdated);;
			}else if(field.equalsIgnoreCase("IMAGEURL") ) {
				//here remove img url of earlier img from CLOUDINARY
				advertisement.setImageUrl(toBeUpdated);;
			} else if(field.equalsIgnoreCase("REDIRECTURL") ) {
				advertisement.setRedirectUrl(toBeUpdated);
			} else if(field.equalsIgnoreCase("ADVERTISEMENTTYPE") ) {
				advertisement.setAdvertisementType(toBeUpdated);;
			}
			
			return advertisementRepo.save(advertisement);
		}
		
		return null;
	}

	@Override
	public List<Advertisement> findByActiveFalseAndStartDateBefore(LocalDateTime now) {
		// TODO Auto-generated method stub
		return advertisementRepo.findByActiveFalseAndStartDateBefore(now);
	}

	@Override
	public List<Advertisement> findByActiveTrueAndEndDateBefore(LocalDateTime now) {
		// TODO Auto-generated method stub
		return advertisementRepo.findByActiveTrueAndEndDateBefore(now);
	}

	@Override
	public Integer getClicksOrImpression(String field, Long advertisementId) {
		Optional<Advertisement> optAdvertisement =advertisementRepo.findById(advertisementId);
		if(optAdvertisement.isPresent()) {
			Advertisement advertisement =optAdvertisement.get();
		if(field.equalsIgnoreCase("CLICK") ) {
		   return   advertisement.getClicks();
		}else if(field.equalsIgnoreCase("IMPRESSION") ) {
			return   advertisement.getImpressions();
		}}
		return null;
	}

	@Override
	public List<Advertisement> getAllAds() {
		// TODO Auto-generated method stub
		return advertisementRepo.findAll();
	}

}
