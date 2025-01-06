package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.OfferEditingInputDTO;
import com.example.focus.DTO.OfferEditingOutputDTO;
import com.example.focus.Model.OfferEditing;
import com.example.focus.Model.Photographer;
import com.example.focus.Model.RequestEditing;
import com.example.focus.Repository.EditorRepository;
import com.example.focus.Repository.OfferEditingRepository;
import com.example.focus.Repository.PhotographerRepository;
import com.example.focus.Repository.RequestEditingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferEditingService {

    private final OfferEditingRepository offerEditingRepository;
    private final RequestEditingRepository requestEditingRepository;
    private final EditorRepository editorRepository;
    private final PhotographerRepository photographerRepository;

    public List<OfferEditingOutputDTO> getAllOffers() {
        List<OfferEditing> offers = offerEditingRepository.findAll();
        List<OfferEditingOutputDTO> offerDTOs = new ArrayList<>();
        for (OfferEditing offer : offers) {
            offerDTOs.add(convertToDTO(offer));
        }
        return offerDTOs;
    }

    public List<OfferEditingOutputDTO> getEditorOffers(Integer editorId) {
        List<OfferEditing> offers = offerEditingRepository.findOfferEditingByEditor_Id(editorId);
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

    public OfferEditingOutputDTO createOffer(OfferEditingInputDTO offerInput, Integer editorId) {
        if (editorRepository.findEditorById(editorId) == null) {
            throw new ApiException("Editor not found");
        }

        OfferEditing offer = new OfferEditing();

        offer.setRequestEditing(requestEditingRepository.findById(offerInput.getRequestId())
                .orElseThrow(() -> new ApiException("Request not found")));
        RequestEditing request = requestEditingRepository.findRequestEditingById(offerInput.getRequestId());

        if (!"AwaitingOffer".equals(request.getStatus())){
            throw new ApiException("Request is not AwaitingOffer");
        }

        offer.setOfferDate(LocalDateTime.now());
        offer.setRequestEditing(requestEditingRepository.findRequestEditingById(offerInput.getRequestId()));
        offer.setOfferedPrice(offerInput.getOfferedPrice());
        offer.setEditor(editorRepository.findEditorById(editorId));
        offer.setEstimatedCompletionTime(offerInput.getEstimatedCompletionTime());
        offer.setStatus("Applied");
        request.setStatus("HasOffer");

        return convertToDTO(offerEditingRepository.save(offer));
    }

    public OfferEditingOutputDTO updateOffer(Integer id, OfferEditingInputDTO offerInput) {
        OfferEditing offer = offerEditingRepository.findById(id).orElseThrow(() -> new ApiException("Offer not found"));
        offer.setOfferedPrice(offerInput.getOfferedPrice());
        offer.setEstimatedCompletionTime(offerInput.getEstimatedCompletionTime());
        return convertToDTO(offerEditingRepository.save(offer));
    }

    public void deleteOffer(Integer id) {
        OfferEditing offer = offerEditingRepository.findById(id).orElseThrow(() -> new ApiException("Offer not found"));
        offerEditingRepository.delete(offer);
    }

    public OfferEditingOutputDTO acceptOffer(Integer offerId,Integer photographerId) {
        Photographer photographer= photographerRepository.findPhotographersById(photographerId);

        OfferEditing offer = offerEditingRepository.findById(offerId)
                .orElseThrow(() -> new ApiException("Offer not found"));
        if (offer.getRequestEditing().getPhotographer()!=photographer){
            throw new ApiException("You are not the request owner of this request");
        }
        if (!"Applied".equals(offer.getStatus())) {
            throw new ApiException("Only offers with 'Applied' status can be accepted.");
        }

        offer.setStatus("Accepted");
        offer.getRequestEditing().setStatus("Active");
        offerEditingRepository.save(offer);

        return convertToDTO(offer);
    }

    public OfferEditingOutputDTO rejectOffer(Integer offerId,Integer photographerId) {

        Photographer photographer= photographerRepository.findPhotographersById(photographerId);
        OfferEditing offer = offerEditingRepository.findById(offerId).orElseThrow(() -> new ApiException("Offer not found"));

        if (offer.getRequestEditing().getPhotographer()!=photographer){
            throw new ApiException("You are not the request owner of this request");
        }

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
        dto.setEditorName(offer.getEditor().getName());
        dto.setStatus(offer.getStatus());
        return dto;
    }
}
