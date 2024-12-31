package com.truxxkart.promotionservice_v1.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truxxkart.promotionservice_v1.entity.Advertisement;
import com.truxxkart.promotionservice_v1.enums.AdPlacement;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
//    List<Advertisement> findByPlacementAndActiveTrueAndStartDateBeforeAndEndDateAfter(
//        AdPlacement placement, LocalDateTime now1, LocalDateTime now2
//    );
//    
    List<Advertisement> findByPlacementAndActiveTrue(AdPlacement placement);
    List<Advertisement> findByActiveTrue();
    
    
    List<Advertisement> findByActiveFalseAndStartDateBefore(LocalDateTime now);

    List<Advertisement> findByActiveTrueAndEndDateBefore(LocalDateTime now);
    
}
