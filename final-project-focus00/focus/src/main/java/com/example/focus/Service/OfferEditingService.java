package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.OfferEditingInputDTO;
import com.example.focus.DTO.OfferEditingOutputDTO;
import com.example.focus.Model.OfferEditing;
import com.example.focus.Repository.OfferEditingRepository;
import com.example.focus.Repository.RequestEditingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferEditingService {

    private final OfferEditingRepository offerEditingRepository;
    private final RequestEditingRepository requestEditingRepository;

    public List<OfferEditingOutputDTO> getAllOffers() {
        List<OfferEditing> offers = offerEditingRepository.findAll();
        List<OfferEditingOutputDTO> offerDTOs = new ArrayList<>();
        for (OfferEditing offer : offers) {
            offerDTOs.add(convertToDTO(offer));
        }
        return offerDTOs;
    }

    public OfferEditingOutputDTO getOfferById(Integer id) {
        OfferEditing offer = offerEditingRepository.findById(id).orElseThrow(() -> new ApiException("Offer not found"));
        return convertToDTO(offer);
    }

    public OfferEditingOutputDTO createOffer(OfferEditingInputDTO offerInput) {
        OfferEditing offer = new OfferEditing();
        offer.setRequestEditing(requestEditingRepository.findById(offerInput.getRequestId())
                .orElseThrow(() -> new ApiException("Request not found")));

        offer.setOfferDate(offerInput.getOfferDate());
        offer.setOfferedPrice(offerInput.getOfferedPrice());
        offer.setEstimatedCompletionTime(offerInput.getEstimatedCompletionTime());
        offer.setStatus("Applied");

        return convertToDTO(offerEditingRepository.save(offer));
    }

    public OfferEditingOutputDTO updateOffer(Integer id, OfferEditingInputDTO offerInput) {
        OfferEditing offer = offerEditingRepository.findById(id).orElseThrow(() -> new ApiException("Offer not found"));
        offer.setOfferDate(offerInput.getOfferDate());
        offer.setOfferedPrice(offerInput.getOfferedPrice());
        offer.setEstimatedCompletionTime(offerInput.getEstimatedCompletionTime());
        return convertToDTO(offerEditingRepository.save(offer));
    }

    public void deleteOffer(Integer id) {
        OfferEditing offer = offerEditingRepository.findById(id).orElseThrow(() -> new ApiException("Offer not found"));
        offerEditingRepository.delete(offer);
    }

    public OfferEditingOutputDTO acceptOffer(Integer offerId) {
        OfferEditing offer = offerEditingRepository.findById(offerId)
                .orElseThrow(() -> new ApiException("Offer not found"));

        if (!"Applied".equals(offer.getStatus())) {
            throw new ApiException("Only offers with 'Applied' status can be accepted.");
        }

        offer.setStatus("Accepted");
        offer.getRequestEditing().setStatus("Active");
        offerEditingRepository.save(offer);

        return convertToDTO(offer);
    }

    public OfferEditingOutputDTO rejectOffer(Integer offerId) {
        OfferEditing offer = offerEditingRepository.findById(offerId)
                .orElseThrow(() -> new ApiException("Offer not found"));

        if (!"Applied".equals(offer.getStatus())) {
            throw new ApiException("Only offers with 'Applied' status can be rejected.");
        }

        offer.setStatus("Rejected");
        offerEditingRepository.save(offer);

        return convertToDTO(offer);
    }

    private OfferEditingOutputDTO convertToDTO(OfferEditing offer) {
        OfferEditingOutputDTO dto = new OfferEditingOutputDTO();
        dto.setRequestId(offer.getRequestEditing().getId());
        dto.setOfferDate(offer.getOfferDate());
        dto.setOfferedPrice(offer.getOfferedPrice());
        dto.setEstimatedCompletionTime(offer.getEstimatedCompletionTime());
        dto.setStatus(offer.getStatus());
        return dto;
    }
}
