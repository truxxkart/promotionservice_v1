package com.truxxkart.promotionservice_v1.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truxxkart.promotionservice_v1.entity.CartItemDto;
import com.truxxkart.promotionservice_v1.entity.OrderItem;
import com.truxxkart.promotionservice_v1.entity.Product;
import com.truxxkart.promotionservice_v1.entity.Promotion;
import com.truxxkart.promotionservice_v1.entity.User;
import com.truxxkart.promotionservice_v1.enums.PromotionType;
import com.truxxkart.promotionservice_v1.repository.PromotionRepository;
import com.truxxkart.promotionservice_v1.repository.UserRepository;
import com.truxxkart.promotionservice_v1.service.PromotionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PromotionServiceImpl implements PromotionService {
	@Autowired
	private PromotionRepository promoRepo;
	@Autowired
	private UserRepository userRepo;

	@Override
	public Promotion createPromotion(Promotion promotion) {
		if (promotion.getStartDate() != null && promotion.getEndDate() != null
				&& promotion.getStartDate().isAfter(promotion.getEndDate())) {
			throw new IllegalArgumentException("Start date must be before end date.");
		}
		return promoRepo.save(promotion);
	}

	@Override
	public boolean validatePromoCode(Promotion promotion, Long userId, Long productId) {
		log.info("Inside Valiate Promo...");
		if (!promotion.isValid()) {
			log.info("Promo is invalid");
			return false;

		}
		if (promotion.getCurrentUsageOfPromo() >= promotion.getMaxUsageOfPromo()
				|| promotion.getCurrentGlobalUsageOfPromo() >= promotion.getMaxGlobalCount()) {
			log.info("Promo has reached max use");
			return false;
		}

		Optional<User> optUser = userRepo.findById(userId);
		if (optUser.isPresent()) {
			User thisUser = optUser.get();
			boolean isPromoAlreadyUsedByUser = thisUser.getPromotionUsage().keySet().stream()
					.anyMatch(promotion.getId()::equals);

			if (isPromoAlreadyUsedByUser) {
				log.info("Promo is invalid for user s1");
				return false;
			}

//			if (!promotion.getEligibleUsersType().contains(thisUser.getUserType())) {
//				log.info("Promo is invalid for user s2");
//				return false;
//			}
		}

		// Check product eligibility
		if (!promotion.isProductApplicable(productId)) {
			log.info("Prduct is not have promo");
			return false;

		}
		log.info("Promo is valid ");
		return true;
	}

	@Override
	public double applyPromoCode(String promoCode, Long userId, List<CartItemDto> cartItemsDtos) {
		Optional<Promotion> optPromo = promoRepo.findByPromoCode(promoCode);
		Optional<User> optUser = userRepo.findById(userId);
		User user = optUser.get();
		Promotion promotion = optPromo.get();
		log.info("APPLY STARTED");
		if (optPromo.isEmpty() || !promotion.getIsActive()) {
			throw new IllegalArgumentException("Invalid or inactive promo code.");
		}
		double totalDiscount = 0;
		if (promotion.getType() == PromotionType.MIN_PURCHASE_AMOUNT) {
			if (!user.getPromotionUsage().containsKey(promotion.getId())) {
//				 totalDiscount = cartItemsDtos.stream().mapToDouble(ci -> {
//						     
//						        return ci.getTotalPrice()*(promotion.getDiscountValue()/100);
//						    }).sum(); 
				for (CartItemDto ci : cartItemsDtos) {
					totalDiscount += promotion.getDiscountValue();
					log.info("APPLY STARTED INSIDE {} ", totalDiscount);
				}

			}

			user.getPromotionUsage().put(promotion.getId(), user.getPromotionUsage().getOrDefault(promotion.getId(), 0) + 1);
			userRepo.save(user);
			promotion.setCurrentUsageOfPromo(promotion.getCurrentUsageOfPromo() + 1);
			return totalDiscount;
		}

		log.info("APPLY STARTED STAGE 2");
		for (CartItemDto ci : cartItemsDtos) {
			log.info("START VALIDATION");
			if (validatePromoCode(promotion, userId, ci.getProductId())) {

				if (promotion.getType() == PromotionType.PERCENTAGE) {
					totalDiscount += (ci.getTotalPrice() * promotion.getDiscountValue()) / 100;
				} else if (promotion.getType() == PromotionType.FIXED_AMOUNT) {
					totalDiscount += promotion.getDiscountValue();
				}
				log.info("APPLY STARTED INSIDE {} ", totalDiscount);
			}
		}

		log.info("APPLY STARTED STAGE 3 {}", totalDiscount);
		user.getPromotionUsage().put(promotion.getId(), user.getPromotionUsage().getOrDefault(promotion.getId(), 0) + 1);
		userRepo.save(user);
		promotion.setCurrentUsageOfPromo(promotion.getCurrentUsageOfPromo() + 1);

		return totalDiscount;

	}

	@Override
	public List<Promotion> getActivePromotions() {
		List<Promotion> promoList = promoRepo.findAll();
		List<Promotion> activePromo = promoList.stream().filter(ap -> ap.getIsActive()).collect(Collectors.toList());
		return activePromo;
	}

	@Override
	public List<Promotion> getAllPromotions() {

		return promoRepo.findAll();
	}

//	return (now.isEqual(startDate) || now.isAfter(startDate)) &&
//		       (now.isEqual(endDate) || now.isBefore(endDate));

	@Override
	public List<Promotion> getCurrentPromotions() {
		LocalDateTime now = LocalDateTime.now();
		return promoRepo.findAll().stream().filter(p -> p.getEndDate().isAfter(now) && p.getStartDate().isBefore(now)).collect(Collectors.toList());
//		return null;
	}
	
	@Override
	public List<Promotion> getExpiredPromotions() {
		LocalDateTime now = LocalDateTime.now();
		return promoRepo.findAll().stream().filter(p -> p.getEndDate().isBefore(now)).collect(Collectors.toList());
//		return null;
	}

	@Override
	public List<Promotion> getUpcomingPromotions() {
		LocalDateTime now = LocalDateTime.now();
		return promoRepo.findAll().stream().filter(p -> p.getStartDate().isAfter(now)).collect(Collectors.toList());
//		return null;
	}

	@Override
	public List<Promotion> getPromotionBy(String field, String findBy) { // title, promocode
		if (field.equalsIgnoreCase("TITLE")) {
			return promoRepo.findAll().stream().filter(p -> p.getTitle().equalsIgnoreCase(findBy))
					.collect(Collectors.toList());
		} else if (field.equalsIgnoreCase("PROMOCODE")) {
			return promoRepo.findAll().stream().filter(p -> p.getPromoCode().equalsIgnoreCase(findBy))
					.collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<Promotion> getPromotionByIds(String field, Long findBy) { // id,cretorId
		if (field.equalsIgnoreCase("PROMOID")) {
			return promoRepo.findAll().stream().filter(p -> p.getId() == findBy).collect(Collectors.toList());
		} else if (field.equalsIgnoreCase("CREATORID")) {
			return promoRepo.findAll().stream().filter(p -> p.getCreatorId() == findBy).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<Promotion> getPromotionByType(PromotionType type) {
		return promoRepo.findAll().stream().filter(p -> p.getType().equals(type)).collect(Collectors.toList());
	}

	@Override
	public List<Promotion> getGlobalPromotion() {
		return promoRepo.findAll().stream().filter(p -> p.getIsGlobal() == true).collect(Collectors.toList());
	}

	@Override
	public Promotion updatePromotionTitle(String promoCode, String newTitle) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			promo.setTitle(newTitle);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionDescription(String promoCode, String newDescription) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			promo.setDescription(newDescription);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionType(String promoCode, PromotionType newType) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			promo.setType(newType);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionDiscountValue(String promoCode, Double discountValue) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			promo.setDiscountValue(discountValue);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionMinimunPurchaseAmount(String promoCode, Double minPurchaseAmount) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			promo.setMinPurchaseAmount(minPurchaseAmount);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromoCode(String promoCode, String newPromoCode) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			promo.setPromoCode(newPromoCode);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionMaxUse(String promoCode, Integer maxUsageOfPromo) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			promo.setMaxUsageOfPromo(maxUsageOfPromo);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionUserType(String promoCode, String field, String userType) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			Set<String> eligibleUserTypes = promo.getEligibleUsersType();
			if (field.equalsIgnoreCase("ADD")) {
				eligibleUserTypes.add(userType);
			} else if (field.equalsIgnoreCase("REMOVE")) {
				if (eligibleUserTypes.contains(userType)) {
					eligibleUserTypes.remove(userType);
				}
			}
			promo.setEligibleUsersType(eligibleUserTypes);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionApplicableProduct(String promoCode, String field, Long productId) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			Set<Long> applicableProducts = promo.getApplicableProducts();
			if (field.equalsIgnoreCase("ADD")) {
				applicableProducts.add(productId);
			} else if (field.equalsIgnoreCase("REMOVE")) {
				if (applicableProducts.contains(productId)) {
					applicableProducts.remove(productId);
				}
			}
			promo.setApplicableProducts(applicableProducts);
			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionDates(String promoCode, String field, LocalDateTime date) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			if (field.equalsIgnoreCase("START")) {
				if(date.isBefore(promo.getEndDate())) {
					promo.setStartDate(date);
				}
				
			} else if (field.equalsIgnoreCase("END")) {
				if(date.isAfter(promo.getStartDate())) {
					promo.setEndDate(date);
				}
			
			}

			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public Promotion updatePromotionStatus(String promoCode, String field, Boolean toBeUpdated) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			if (field.equalsIgnoreCase("ACTIVATION")) {
				promo.setIsActive(toBeUpdated);
			} else if (field.equalsIgnoreCase("VERIFICATION")) {
				promo.setIsVerified(toBeUpdated);
			}else if (field.equalsIgnoreCase("GLOBAL")) {
				promo.setIsGlobal(toBeUpdated);
			}

			return promoRepo.save(promo);
		}
		return null;
	}

	@Override
	public String deletePromotion(String promoCode) {
		Optional<Promotion> optPromotion = promoRepo.findByPromoCode(promoCode);
		if (optPromotion.isPresent()) {
			Promotion promo = optPromotion.get();
			 promoRepo.delete(promo);
			 return "Promotion is deletd successfully";
		}
		return null;
	}

	

}
