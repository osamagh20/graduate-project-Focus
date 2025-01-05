package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.RequestEditingInputDTO;
import com.example.focus.DTO.RequestEditingOutputDTO;
import com.example.focus.Model.RequestEditing;
import com.example.focus.Repository.EditorRepository;
import com.example.focus.Repository.PhotographerRepository;
import com.example.focus.Repository.RequestEditingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestEditingService {

    private final RequestEditingRepository requestEditingRepository;
    private final EditorRepository editorRepository;
    private final PhotographerRepository photographerRepository;

    public List<RequestEditingOutputDTO> getAllRequests() {
        List<RequestEditing> requests = requestEditingRepository.findAll();
        List<RequestEditingOutputDTO> requestDTOs = new ArrayList<>();
        for (RequestEditing request : requests) {
            requestDTOs.add(convertToDTO(request));
        }
        return requestDTOs;
    }

    public List<RequestEditingOutputDTO> getEditorRequests(Integer id) {
        List<RequestEditing> requests = requestEditingRepository.findRequestEditingsByEditor_Id(id);
        List<RequestEditingOutputDTO> requestDTOs = new ArrayList<>();
        for (RequestEditing request : requests) {
            requestDTOs.add(convertToDTO(request));
        }
        return requestDTOs;
    }

    public List<RequestEditingOutputDTO> getPhotographerRequests(Integer id) {
        List<RequestEditing> requests = requestEditingRepository.findRequestEditingsByPhotographerId(id);
        List<RequestEditingOutputDTO> requestDTOs = new ArrayList<>();
        for (RequestEditing request : requests) {
            requestDTOs.add(convertToDTO(request));
        }
        return requestDTOs;
    }

    public List<RequestEditingOutputDTO> getAwaitingOfferRequestsForEditor(Integer id) {
        List<RequestEditing> requests = requestEditingRepository.findRequestEditingsByEditor_IdAndStatus(id,"AwaitingOffer");
        List<RequestEditingOutputDTO> requestDTOs = new ArrayList<>();
        for (RequestEditing request : requests) {
            requestDTOs.add(convertToDTO(request));
        }
        return requestDTOs;
    }

    public RequestEditingOutputDTO getRequestById(Integer id) {
        RequestEditing request = requestEditingRepository.findById(id).orElseThrow(() -> new ApiException("Request not found"));
        return convertToDTO(request);
    }

    public RequestEditingOutputDTO createRequest(RequestEditingInputDTO requestInput,Integer editorId,Integer photographerId) {
        if (editorRepository.findEditorById(editorId) == null) {
            throw new ApiException("Editor not found");
        }
        if (photographerRepository.findPhotographersById(photographerId) == null) {
            throw new ApiException("Photographer not found");
        }
        RequestEditing request = new RequestEditing();
        request.setEditor(editorRepository.findEditorById(editorId));
        request.setPhotographer(photographerRepository.findPhotographersById(photographerId));
        request.setEstimatedCompletionDate(requestInput.getEstimatedCompletionDate());
        request.setDescription(requestInput.getDescription());
        request.setFullCameraName(requestInput.getFullCameraName());
        request.setSensorSize(requestInput.getSensorSize());
        request.setKitLens(requestInput.getKitLens());
        request.setViewFinder(requestInput.getViewFinder());
        request.setNativeISO(requestInput.getNativeISO());
        request.setStatus("AwaitingOffer");
        return convertToDTO(requestEditingRepository.save(request));
    }

    public RequestEditingOutputDTO updateRequest(Integer id, RequestEditingInputDTO requestInput) {
        RequestEditing request = requestEditingRepository.findById(id).orElseThrow(() -> new ApiException("Request not found"));
        request.setEstimatedCompletionDate(requestInput.getEstimatedCompletionDate());
        request.setDescription(requestInput.getDescription());
        request.setFullCameraName(requestInput.getFullCameraName());
        request.setSensorSize(requestInput.getSensorSize());
        request.setKitLens(requestInput.getKitLens());
        request.setViewFinder(requestInput.getViewFinder());
        request.setNativeISO(requestInput.getNativeISO());
        return convertToDTO(requestEditingRepository.save(request));
    }

    public void deleteRequest(Integer id) {
        RequestEditing request = requestEditingRepository.findById(id).orElseThrow(() -> new ApiException("Request not found"));
        requestEditingRepository.delete(request);
    }

    public RequestEditingOutputDTO acceptRequest(Integer requestId) {
        RequestEditing request = requestEditingRepository.findById(requestId)
                .orElseThrow(() -> new ApiException("Request not found"));

        if (!"Pending".equals(request.getStatus())) {
            throw new ApiException("Only requests with 'Pending' status can be accepted.");
        }

        request.setStatus("AwaitingOffer");
        requestEditingRepository.save(request);

        return convertToDTO(request);
    }

    public RequestEditingOutputDTO rejectRequest(Integer requestId) {
        RequestEditing request = requestEditingRepository.findById(requestId)
                .orElseThrow(() -> new ApiException("Request not found"));

        if (!"Pending".equals(request.getStatus())) {
            throw new ApiException("Only requests with 'Pending' status can be rejected.");
        }

        request.setStatus("Closed");
        requestEditingRepository.save(request);

        return convertToDTO(request);
    }

    private RequestEditingOutputDTO convertToDTO(RequestEditing request) {
        RequestEditingOutputDTO dto = new RequestEditingOutputDTO();
        dto.setEstimatedCompletionDate(request.getEstimatedCompletionDate());
        dto.setDescription(request.getDescription());
        dto.setFullCameraName(request.getFullCameraName());
        dto.setSensorSize(request.getSensorSize());
        dto.setKitLens(request.getKitLens());
        dto.setViewFinder(request.getViewFinder());
        dto.setNativeISO(request.getNativeISO());
        dto.setEditorName(request.getEditor().getName());
        dto.setPhotographeName(request.getPhotographer().getName());
        dto.setStatus(request.getStatus());
        return dto;
    }
}
