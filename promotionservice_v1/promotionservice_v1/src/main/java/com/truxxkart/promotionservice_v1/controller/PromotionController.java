package com.truxxkart.promotionservice_v1.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truxxkart.promotionservice_v1.entity.CartItemDto;
import com.truxxkart.promotionservice_v1.entity.Promotion;
import com.truxxkart.promotionservice_v1.enums.PromotionType;
import com.truxxkart.promotionservice_v1.service.PromotionService;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
	
	@Autowired
	private PromotionService promoService;
	
	@PostMapping()
	public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion){
		Promotion createdPromotion =promoService.createPromotion(promotion);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPromotion);
	}
	
	@PostMapping("/apply")
	public ResponseEntity<Double> applyPromoCode(@RequestParam String promoCode,@RequestParam Long userId,@RequestBody List<CartItemDto> cartItemDtos){
		Double discountedPrice =promoService.applyPromoCode(promoCode, userId, cartItemDtos);
		return ResponseEntity.status(HttpStatus.OK).body(discountedPrice);
	}
	
	@GetMapping("/active")
	public ResponseEntity<List<Promotion>> getAllActivePromotions(){
		List<Promotion> promoList =promoService.getActivePromotions();
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/all")
	public ResponseEntity<List<Promotion>> getAllPromotions(){
		List<Promotion> promoList =promoService.getAllPromotions();
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/current")
	public ResponseEntity<List<Promotion>> getCurrentPromotions(){    
		List<Promotion> promoList =promoService.getCurrentPromotions();
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/expired")
	public ResponseEntity<List<Promotion>> getExpiredPromotions(){
		List<Promotion> promoList =promoService.getExpiredPromotions();
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/upcoming")
	public ResponseEntity<List<Promotion>> getUpcomingPromotions(){    
		List<Promotion> promoList =promoService.getUpcomingPromotions();
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/by")   // title, promocode
	public ResponseEntity<List<Promotion>> getPromotionBy(@RequestParam String field ,@RequestParam String findBy){
		List<Promotion> promoList =promoService.getPromotionBy(field, findBy);
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/ids")  // id,cretorId
	public ResponseEntity<List<Promotion>> getPromotionByIds(@RequestParam String field ,@RequestParam Long  findBy){
		List<Promotion> promoList =promoService.getPromotionByIds(field, findBy);
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/type")
	public ResponseEntity<List<Promotion>> getPromotionByType(@RequestParam PromotionType type){
		List<Promotion> promoList =promoService.getPromotionByType(type);
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	@GetMapping("/global")
	public ResponseEntity<List<Promotion>> getGlobalPromotion(){
		List<Promotion> promoList =promoService.getGlobalPromotion();
		return ResponseEntity.status(HttpStatus.OK).body(promoList);
	}
	
	@PutMapping("/update/title")
	public ResponseEntity<Promotion> updatePromotionTitle(@RequestParam String promoCode,@RequestParam String newTitle){
		Promotion createdPromotion =promoService.updatePromotionTitle(promoCode, newTitle);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/description")
	public ResponseEntity<Promotion> updatePromotionDescription(@RequestParam String promoCode,@RequestParam String newDescription){
		Promotion createdPromotion =promoService.updatePromotionDescription(promoCode, newDescription);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/type")
	public ResponseEntity<Promotion> updatePromotionType(@RequestParam String promoCode,@RequestParam PromotionType newType){
		Promotion createdPromotion =promoService.updatePromotionType(promoCode, newType);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/discount-value")
	public ResponseEntity<Promotion> updatePromotionDiscountValue(@RequestParam String promoCode,@RequestParam Double discountValue){
		Promotion createdPromotion =promoService.updatePromotionDiscountValue(promoCode, discountValue);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/min-purchase-amount")
	public ResponseEntity<Promotion> updatePromotionMinimunPurchaseAmount(@RequestParam String promoCode, @RequestParam Double minPurchaseAmount){
		Promotion createdPromotion =promoService.updatePromotionMinimunPurchaseAmount(promoCode, minPurchaseAmount);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	@PutMapping("/update/promo-code")
	public ResponseEntity<Promotion> updatePromoCode(@RequestParam String promoCode,@RequestParam String newPromoCode){
		Promotion createdPromotion =promoService.updatePromoCode(promoCode, newPromoCode);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/max-use")
	public ResponseEntity<Promotion> updatePromotionMaxUse(@RequestParam String promoCode,@RequestParam Integer maxUsageOfPromo){
		Promotion createdPromotion =promoService.updatePromotionMaxUse(promoCode, maxUsageOfPromo);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/user-type")
	public ResponseEntity<Promotion> updatePromotionUserType(@RequestParam String promoCode,@RequestParam String field,@RequestParam String userType){
		Promotion createdPromotion =promoService.updatePromotionUserType(promoCode, field, userType);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/applicable-products")
	public ResponseEntity<Promotion> updatePromotionApplicableProduct(@RequestParam String promoCode,@RequestParam String field ,@RequestParam Long productId){
		Promotion createdPromotion =promoService.updatePromotionApplicableProduct(promoCode, field, productId);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/promotion-dates")
	public ResponseEntity<Promotion> updatePromotionDates(@RequestParam String promoCode,@RequestParam String field,@RequestParam LocalDateTime date){
		Promotion createdPromotion =promoService.updatePromotionDates(promoCode, field, date);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@PutMapping("/update/promotion-status")   //activation , verifiv=cation .global
	public ResponseEntity<Promotion> updatePromotionStatu(@RequestParam String promoCode,@RequestParam String field,@RequestParam Boolean toBeUpdated){
		Promotion createdPromotion =promoService.updatePromotionStatus(promoCode, field, toBeUpdated);
		return ResponseEntity.status(HttpStatus.OK).body(createdPromotion);
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<String> deletePromotion(@RequestParam String promoCode){
		String delMsg =promoService.deletePromotion(promoCode);
		return ResponseEntity.status(HttpStatus.OK).body(delMsg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
