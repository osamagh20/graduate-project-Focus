package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.OfferEditingInputDTO;
import com.example.focus.DTO.OfferEditingOutputDTO;
import com.example.focus.Model.OfferEditing;
import com.example.focus.Model.Editor;
import com.example.focus.Repository.OfferEditingRepository;
import com.example.focus.Repository.EditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferEditingService {

    private final OfferEditingRepository offerEditingRepository;
    private final EditorRepository editorRepository;

    public List<OfferEditingOutputDTO> getAllOffers() {
        List<OfferEditing> offers = offerEditingRepository.findAll();
        List<OfferEditingOutputDTO> offerDTOs = new ArrayList<>();
        for (OfferEditing offer : offers) {
            offerDTOs.add(convertToDTO(offer));
        }
        return offerDTOs;
    }

    public OfferEditingOutputDTO getOfferById(Integer id) {
        OfferEditing offer = offerEditingRepository.findById(id).orElse(null);
        if (offer == null) {
            throw new ApiException("Offer not found");
        }
        return convertToDTO(offer);
    }

    public OfferEditingOutputDTO createOffer(OfferEditingInputDTO offerInput) {
        Editor editor = editorRepository.findById(offerInput.getEditorId())
                .orElseThrow(() -> new ApiException("Editor not found"));

        OfferEditing offer = new OfferEditing();
        offer.setEditor(editor);
        offer.setOfferDate(offerInput.getOfferDate());
        offer.setOfferedPrice(offerInput.getOfferedPrice());
        offer.setEstimatedCompletionTime(offerInput.getEstimatedCompletionTime());
        offer.setStatus("Applied");

        return convertToDTO(offerEditingRepository.save(offer));
    }

    public OfferEditingOutputDTO updateOffer(Integer id, OfferEditingInputDTO offerInput) {
        OfferEditing offer = offerEditingRepository.findById(id).orElse(null);
        if (offer == null) {
            throw new ApiException("Offer not found");
        }

        offer.setOfferDate(offerInput.getOfferDate());
        offer.setOfferedPrice(offerInput.getOfferedPrice());
        offer.setEstimatedCompletionTime(offerInput.getEstimatedCompletionTime());
        offer.setStatus(offerInput.getStatus());

        return convertToDTO(offerEditingRepository.save(offer));
    }

    public void deleteOffer(Integer id) {
        OfferEditing offer = offerEditingRepository.findById(id).orElse(null);
        if (offer == null) {
            throw new ApiException("Offer not found");
        }
        offerEditingRepository.delete(offer);
    }

    public List<OfferEditingOutputDTO> getOffersByEditorId(Integer editorId) {
        Editor editor = editorRepository.findById(editorId).orElse(null);
        if (editor == null) {
            throw new ApiException("Editor not found");
        }

        List<OfferEditing> offers = offerEditingRepository.findOfferEditingByEditor_Id(editorId);
        List<OfferEditingOutputDTO> offerDTOs = new ArrayList<>();
        for (OfferEditing offer : offers) {
            offerDTOs.add(convertToDTO(offer));
        }
        return offerDTOs;
    }

    private OfferEditingOutputDTO convertToDTO(OfferEditing offer) {
        OfferEditingOutputDTO dto = new OfferEditingOutputDTO();
        dto.setId(offer.getId());
        dto.setEditorId(offer.getEditor().getId());
        dto.setOfferDate(offer.getOfferDate());
        dto.setOfferedPrice(offer.getOfferedPrice());
        dto.setEstimatedCompletionTime(offer.getEstimatedCompletionTime());
        dto.setStatus(offer.getStatus());
        return dto;
    }
}
