package com.truxxkart.promotionservice_v1.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.truxxkart.promotionservice_v1.entity.Advertisement;
import com.truxxkart.promotionservice_v1.repository.AdvertisementRepository;

@Component
public class ScheduledTasks {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateAdStatuses() {
        List<Advertisement> ads = advertisementRepository.findAll();
        for (Advertisement ad : ads) {
            boolean shouldBeActive = ad.getStartDate().isBefore(LocalDateTime.now())
                    && ad.getEndDate().isAfter(LocalDateTime.now());
            ad.setActive(shouldBeActive);
            advertisementRepository.save(ad);
        }
    }
}
