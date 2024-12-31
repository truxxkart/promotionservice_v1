package com.truxxkart.promotionservice_v1.serviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.truxxkart.promotionservice_v1.entity.Advertisement;
import com.truxxkart.promotionservice_v1.repository.AdvertisementRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AdvertisementScheduler {

	@Autowired
    private AdvertisementRepository advertisementRepository;

   

    // Runs every minute to check and update advertisement statuses
    @Scheduled(cron = "0 * * * * *") // Runs at the start of every minute
    public void updateAdvertisementStatuses() {
        LocalDateTime now = LocalDateTime.now();

        // Activate advertisements whose startDate has arrived
        List<Advertisement> adsToActivate = advertisementRepository.findByActiveFalseAndStartDateBefore(now);
        adsToActivate.forEach(ad -> {
            ad.setActive(true);
           log.info("ADVERTISEMENT ACTIVATED {}" , ad.getTitle());
        });

        // Deactivate advertisements whose endDate has passed
        List<Advertisement> adsToDeactivate = advertisementRepository.findByActiveTrueAndEndDateBefore(now);
        adsToDeactivate.forEach(ad -> {
            ad.setActive(false);
            log.info("ADVERTISEMENT DEACTIVATED {} " , ad.getTitle());
        });

        // Save changes in bulk for optimization
        advertisementRepository.saveAll(adsToActivate);
        advertisementRepository.saveAll(adsToDeactivate);
    }
}
