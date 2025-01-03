package com.truxxkart.promotionservice_v1.service;

import java.time.LocalDateTime;
import java.util.List;
import com.truxxkart.promotionservice_v1.entity.CartItemDto;
import com.truxxkart.promotionservice_v1.entity.Promotion;
import com.truxxkart.promotionservice_v1.enums.PromotionType;

public interface PromotionService {
	
	 public Promotion createPromotion(Promotion promotion) ;
	 
	// Validate if a promo code can be applied for a given user and product.
    public boolean validatePromoCode(Promotion promotion, Long userId, Long productId);
    
    public double applyPromoCode(String promoCode, Long userId, List<CartItemDto> cartItemsDtos);
    
    public List<Promotion> getAllPromotions();
	public List<Promotion> getActivePromotions();
	public List<Promotion> getExpiredPromotions();
	public List<Promotion> getUpcomingPromotions();
	public List<Promotion> getCurrentPromotions();
	public List<Promotion> getPromotionBy(String field ,String findBy); //title,promocode
	public List<Promotion> getPromotionByIds(String field ,Long findBy);  //id,creatorId
	public List<Promotion> getPromotionByType(PromotionType type);
	public List<Promotion> getGlobalPromotion();
	
	public Promotion updatePromotionTitle(String promoCode,String newTitle);
	public Promotion updatePromotionDescription(String promoCode,String newDescription);
	public Promotion updatePromotionType(String promoCode,PromotionType newType);
	public Promotion updatePromotionDiscountValue(String promoCode,Double discountValue);
	public Promotion updatePromotionMinimunPurchaseAmount(String promoCode,Double minPurchaseAmount);
	public Promotion updatePromoCode(String promoCode,String newPromoCode);
	public Promotion updatePromotionMaxUse(String promoCode,Integer maxUsageOfPromo);
	public Promotion updatePromotionUserType(String promoCode,String field,String userType); //field for add/remove
	public Promotion updatePromotionApplicableProduct(String promoCode,String field ,Long productId);  //field for add/remove
	public Promotion updatePromotionDates(String promoCode,String field,LocalDateTime date); // field for start/end date
	public Promotion updatePromotionStatus(String promoCode,String field,Boolean toBeUpdated);
	
	public String deletePromotion(String promoCode);

	//•	Monitor usage rates, revenue impact, and customer engagement
	//Generate weekly reports to measure the revenue generated by active promotions.
	//o	Automatically activate promotions on specified intervals.
	
	
}
