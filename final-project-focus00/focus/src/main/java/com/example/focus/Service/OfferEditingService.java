package com.example.focus.Service;

import com.example.focus.DTO.OfferEditingInputDTO;
import com.example.focus.DTO.OfferEditingOutputDTO;
import com.example.focus.Model.OfferEditing;
import com.example.focus.Model.RequestEditing;
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

    public OfferEditingOutputDTO createOffer(OfferEditingInputDTO offerEditingInputDTO) {
        RequestEditing requestEditing = validateRequest(offerEditingInputDTO.getRequestId());
        if (!"Pending".equals(requestEditing.getStatus())) {
            throw new IllegalArgumentException("Cannot create an offer for this request");
        }

        OfferEditing offerEditing = new OfferEditing();
        offerEditing.setRequestEditing(requestEditing);
        offerEditing.setOfferDate(offerEditingInputDTO.getOfferDate());
        offerEditing.setOfferedPrice(offerEditingInputDTO.getOfferedPrice());
        offerEditing.setEstimatedCompletionTime(offerEditingInputDTO.getEstimatedCompletionTime());
        offerEditing.setStatus("Applied");

        OfferEditing savedOffer = offerEditingRepository.save(offerEditing);
        requestEditing.setStatus("Offer Sent");
        requestEditingRepository.save(requestEditing);

        return convertToDTO(savedOffer);
    }

    public List<OfferEditingOutputDTO> getAllOffers() {
        List<OfferEditing> offers = offerEditingRepository.findAll();
        List<OfferEditingOutputDTO> offerDTOs = new ArrayList<>();
        for (OfferEditing offer : offers) {
            offerDTOs.add(convertToDTO(offer));
        }
        return offerDTOs;
    }

    public OfferEditingOutputDTO getOfferById(Integer id) {
        OfferEditing offerEditing = offerEditingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));
        return convertToDTO(offerEditing);
    }

    public OfferEditingOutputDTO updateOffer(Integer id, OfferEditingInputDTO offerEditingInputDTO) {
        OfferEditing offerEditing = offerEditingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

        RequestEditing requestEditing = validateRequest(offerEditingInputDTO.getRequestId());
        if (!"Pending".equals(requestEditing.getStatus()) && !"Offer Sent".equals(requestEditing.getStatus())) {
            throw new IllegalArgumentException("Cannot update an offer for this request");
        }

        offerEditing.setOfferDate(offerEditingInputDTO.getOfferDate());
        offerEditing.setOfferedPrice(offerEditingInputDTO.getOfferedPrice());
        offerEditing.setEstimatedCompletionTime(offerEditingInputDTO.getEstimatedCompletionTime());
        offerEditing.setStatus("Updated");

        OfferEditing savedOffer = offerEditingRepository.save(offerEditing);
        return convertToDTO(savedOffer);
    }

    public void deleteOffer(Integer id) {
        OfferEditing offerEditing = offerEditingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

        offerEditingRepository.delete(offerEditing);
    }

    private RequestEditing validateRequest(Integer requestId) {
        return requestEditingRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }

    private OfferEditingOutputDTO convertToDTO(OfferEditing offerEditing) {
        OfferEditingOutputDTO offerOutputDTO = new OfferEditingOutputDTO();
        offerOutputDTO.setId(offerEditing.getId());
        offerOutputDTO.setRequestId(offerEditing.getRequestEditing().getId());
        offerOutputDTO.setOfferDate(offerEditing.getOfferDate());
        offerOutputDTO.setOfferedPrice(offerEditing.getOfferedPrice());
        offerOutputDTO.setEstimatedCompletionTime(offerEditing.getEstimatedCompletionTime());
        offerOutputDTO.setStatus(offerEditing.getStatus());
        return offerOutputDTO;
    }
}
