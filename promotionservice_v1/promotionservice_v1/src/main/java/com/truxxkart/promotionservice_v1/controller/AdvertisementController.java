package com.truxxkart.promotionservice_v1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truxxkart.promotionservice_v1.entity.Advertisement;
import com.truxxkart.promotionservice_v1.enums.AdPlacement;
import com.truxxkart.promotionservice_v1.service.AdvertisementService;

import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    public ResponseEntity<Advertisement> createAdvertisement(@RequestBody Advertisement dto) {
        Advertisement response = advertisementService.createAdvertisement(dto);
        return ResponseEntity.ok(response);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.noContent().build();
    }
}

