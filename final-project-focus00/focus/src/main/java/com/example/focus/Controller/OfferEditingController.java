package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.OfferEditingInputDTO;
import com.example.focus.DTO.OfferEditingOutputDTO;
import com.example.focus.Model.MyUser;
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

    @GetMapping("/get-all")
    public ResponseEntity getAllOffers() {
        List<OfferEditingOutputDTO> offers = offerEditingService.getAllOffers();
        return ResponseEntity.status(200).body(offers);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getOfferById(@PathVariable Integer id) {
        OfferEditingOutputDTO offer = offerEditingService.getOfferById(id);
        return ResponseEntity.status(200).body(offer);
    }

    @GetMapping("/get-by-editor/{id}")
    public ResponseEntity getEditorOffer(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(offerEditingService.getEditorOffers(id));
    }

    @PostMapping("/create/{id}")
    public ResponseEntity createOffer(@RequestBody @Valid OfferEditingInputDTO offerInput, @PathVariable Integer id) {
        OfferEditingOutputDTO createdOffer = offerEditingService.createOffer(offerInput,id);
        return ResponseEntity.status(200).body(createdOffer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOffer(@PathVariable Integer id, @RequestBody @Valid OfferEditingInputDTO offerInput) {
        OfferEditingOutputDTO updatedOffer = offerEditingService.updateOffer(id, offerInput);
        return ResponseEntity.status(200).body(updatedOffer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOffer(@PathVariable Integer id) {
        offerEditingService.deleteOffer(id);
        return ResponseEntity.status(200).body(new ApiResponse("Offer deleted successfully"));
    }

    @PutMapping("/accept/{offerId}")
    public ResponseEntity acceptOffer(@PathVariable Integer offerId, MyUser auth) {
        OfferEditingOutputDTO acceptedOffer = offerEditingService.acceptOffer(offerId,auth.getId());
        return ResponseEntity.status(200).body(acceptedOffer);
    }

    @PutMapping("/reject/{offerId}")
    public ResponseEntity rejectOffer(@PathVariable Integer offerId, MyUser auth) {
        OfferEditingOutputDTO rejectedOffer = offerEditingService.rejectOffer(offerId,auth.getId());
        return ResponseEntity.status(200).body(rejectedOffer);
    }
}
