package com.truxxkart.promotionservice_v1.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truxxkart.promotionservice_v1.entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
//    List<Promotion> findByActiveTrueAndStartDateBeforeAndEndDateAfter(LocalDateTime now1, LocalDateTime now2);
//    List<Promotion> findByEligibleUsers_Id(Long userId);
    Optional<Promotion> findByPromoCode(String promoCode);
}
