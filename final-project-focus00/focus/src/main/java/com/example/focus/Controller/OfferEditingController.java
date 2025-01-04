package com.example.focus.Controller;

import com.example.focus.DTO.OfferEditingInputDTO;
import com.example.focus.DTO.OfferEditingOutputDTO;
import com.example.focus.Service.OfferEditingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/focus/offer-editing")
@RequiredArgsConstructor
public class OfferEditingController {

    private final OfferEditingService offerEditingService;

    @PostMapping
    public ResponseEntity createOffer(@RequestBody @Valid OfferEditingInputDTO offerEditingInputDTO) {
        OfferEditingOutputDTO createdOffer = offerEditingService.createOffer(offerEditingInputDTO);
        return ResponseEntity.status(200).body(createdOffer);
    }

    @GetMapping
    public ResponseEntity getAllOffers() {
        List<OfferEditingOutputDTO> offers = offerEditingService.getAllOffers();
        return ResponseEntity.status(200).body(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOfferById(@PathVariable Integer id) {
        OfferEditingOutputDTO offer = offerEditingService.getOfferById(id);
        return ResponseEntity.status(200).body(offer);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOffer(@PathVariable Integer id, @RequestBody @Valid OfferEditingInputDTO offerEditingInputDTO) {
        OfferEditingOutputDTO updatedOffer = offerEditingService.updateOffer(id, offerEditingInputDTO);
        return ResponseEntity.status(200).body(updatedOffer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOffer(@PathVariable Integer id) {
        offerEditingService.deleteOffer(id);
        return ResponseEntity.status(200).body("Offer deleted successfully");
    }
}
