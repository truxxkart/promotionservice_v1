package com.truxxkart.promotionservice_v1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truxxkart.promotionservice_v1.entity.Advertisement;
import com.truxxkart.promotionservice_v1.enums.AdPlacement;
import com.truxxkart.promotionservice_v1.service.AdvertisementService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {

	  @Autowired
	    private AdvertisementService advertisementService;
	     //ADD PINCODE FEATURES
	    @PostMapping
	    public ResponseEntity<Advertisement> createAdvertisement(@RequestBody Advertisement dto) {
	        Advertisement response = advertisementService.createAdvertisement(dto);
	        return ResponseEntity.ok(response);
	    }

	    @GetMapping("/all")
	    public ResponseEntity<List<Advertisement>> getAllAdverisements() {
	        List<Advertisement> activeAds = advertisementService.getAllAds();
	        return ResponseEntity.ok(activeAds);
	    }
	    
	    @GetMapping("/id/{id}")
	    public ResponseEntity<Advertisement> getAdvertisement(@PathVariable Long id) {
	        Advertisement response = advertisementService.findAdvertisementById(id);
	        return ResponseEntity.ok(response);
	    }

	    @GetMapping("/active")
	    public ResponseEntity<List<Advertisement>> getActiveAdvertisements(
	            @RequestParam(required = false) AdPlacement placement) {
	        List<Advertisement> activeAds = advertisementService.getActiveAdvertisements(placement);
	        return ResponseEntity.ok(activeAds);
	    }

	    @PostMapping("/{id}/impression")
	    public ResponseEntity<Void> trackImpression(@PathVariable Long id) {
	        advertisementService.trackImpression(id);
	        return ResponseEntity.noContent().build();
	    }

	    @PostMapping("/{id}/click")
	    public ResponseEntity<Void> trackClick(@PathVariable Long id) {
	        advertisementService.trackClick(id);
	        return ResponseEntity.noContent().build();
	    }

	    @GetMapping("/placement")
	    public ResponseEntity<List<Advertisement>> getAdsByPlacement(
	            @RequestParam(required = false) AdPlacement placement) {
	        List<Advertisement> activeAds = advertisementService.getAdsByPlacement(placement);
	        return ResponseEntity.ok(activeAds);
	    }
	    @GetMapping("/status")
	    public ResponseEntity<List<Advertisement>> findAdvertisementByStatus(@RequestParam(required = false)String field , @RequestParam(required = false) Boolean findBy ) {
	        List<Advertisement> activeAds = advertisementService.findAdvertisementByStatus(field, findBy);
	        return ResponseEntity.ok(activeAds);
	    }
	    @GetMapping("/type")
	    public ResponseEntity<List<Advertisement>> findAdvertisementByType(@RequestParam(required = false)String type ) {
	        List<Advertisement> activeAds = advertisementService.findAdvertisementByType(type);
	        return ResponseEntity.ok(activeAds);
	    }
	    
	    @GetMapping("/date")
	    public ResponseEntity<List<Advertisement>> findAdvertisementByDate(@RequestParam(required = false)String field , @RequestParam(required = false) LocalDate date ) {
	        List<Advertisement> activeAds = advertisementService.findByDate(field, date);
	        return ResponseEntity.ok(activeAds);
	    }
	    
	    @GetMapping("/tracks")
	    public ResponseEntity<Integer> getClicksOrImpression(@RequestParam String field,@RequestParam Long advertisementId) {
	        Integer response = advertisementService.getClicksOrImpression(field, advertisementId);
	        return ResponseEntity.ok(response);
	    }
	    
	    @GetMapping("/active/date")
	    public ResponseEntity<List<Advertisement>> findByActiveFalseAndStartDateBefore(@RequestParam LocalDateTime now) {
	        List<Advertisement> activeAds = advertisementService.findByActiveFalseAndStartDateBefore(now);
	        return ResponseEntity.ok(activeAds);
	    }
	    @GetMapping("/active/date/not")
	    public ResponseEntity<List<Advertisement>> findByActiveTrueAndEndDateBefore(@RequestParam LocalDateTime now) {
	        List<Advertisement> activeAds = advertisementService.findByActiveTrueAndEndDateBefore(now);
	        return ResponseEntity.ok(activeAds);
	    }
	    
	    
	    
	    @PutMapping("/update/status")
	    public ResponseEntity<Advertisement> updateAdvertisementStatus(@RequestParam Long id,@RequestParam String field,@RequestParam Boolean toBeUpdated) {
	        Advertisement response = advertisementService.updateAdvertisementStatus(id, field, toBeUpdated);
	        return ResponseEntity.ok(response);
	    }
	    
	    @PutMapping("/update/field")
	    public ResponseEntity<Advertisement> updateAdvertisementField(@RequestParam Long id,@RequestParam String field,@RequestParam String toBeUpdated) {
	        Advertisement response = advertisementService.updateAdvertisementField(id, field, toBeUpdated);
	        return ResponseEntity.ok(response);
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
	        advertisementService.deleteAdvertisement(id);
	        return ResponseEntity.noContent().build();
	    }
	}

