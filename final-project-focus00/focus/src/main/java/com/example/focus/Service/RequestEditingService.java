package com.example.focus.Service;

import com.example.focus.DTO.RequestEditingInputDTO;
import com.example.focus.DTO.RequestEditingOutputDTO;
import com.example.focus.Model.RequestEditing;
import com.example.focus.Repository.RequestEditingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestEditingService {

    private final RequestEditingRepository requestEditingRepository;

    public RequestEditingOutputDTO createRequest(RequestEditingInputDTO requestEditingInputDTO) {
        RequestEditing requestEditing = new RequestEditing();
        requestEditing.setEstimatedCompletionDate(requestEditingInputDTO.getEstimatedCompletionDate());
        requestEditing.setDescription(requestEditingInputDTO.getDescription());
        requestEditing.setFullCameraName(requestEditingInputDTO.getFullCameraName());
        requestEditing.setSensorSize(requestEditingInputDTO.getSensorSize());
        requestEditing.setKitLens(requestEditingInputDTO.getKitLens());
        requestEditing.setViewFinder(requestEditingInputDTO.getViewFinder());
        requestEditing.setNativeISO(requestEditingInputDTO.getNativeISO());
        requestEditing.setStatus(requestEditingInputDTO.getStatus());

        RequestEditing savedRequest = requestEditingRepository.save(requestEditing);
        return convertToDTO(savedRequest);
    }

    public List<RequestEditingOutputDTO> getAllRequests() {
        List<RequestEditing> requests = requestEditingRepository.findAll();
        List<RequestEditingOutputDTO> requestDTOs = new ArrayList<>();
        for (RequestEditing request : requests) {
            requestDTOs.add(convertToDTO(request));
        }
        return requestDTOs;
    }

    public RequestEditingOutputDTO getRequestById(Integer id) {
        RequestEditing requestEditing = requestEditingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        return convertToDTO(requestEditing);
    }

    public RequestEditingOutputDTO updateRequest(Integer id, RequestEditingInputDTO requestEditingInputDTO) {
        RequestEditing requestEditing = requestEditingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        requestEditing.setEstimatedCompletionDate(requestEditingInputDTO.getEstimatedCompletionDate());
        requestEditing.setDescription(requestEditingInputDTO.getDescription());
        requestEditing.setFullCameraName(requestEditingInputDTO.getFullCameraName());
        requestEditing.setSensorSize(requestEditingInputDTO.getSensorSize());
        requestEditing.setKitLens(requestEditingInputDTO.getKitLens());
        requestEditing.setViewFinder(requestEditingInputDTO.getViewFinder());
        requestEditing.setNativeISO(requestEditingInputDTO.getNativeISO());
        requestEditing.setStatus(requestEditingInputDTO.getStatus());

        RequestEditing updatedRequest = requestEditingRepository.save(requestEditing);
        return convertToDTO(updatedRequest);
    }

    public void deleteRequest(Integer id) {
        RequestEditing requestEditing = requestEditingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        requestEditingRepository.delete(requestEditing);
    }

    private RequestEditingOutputDTO convertToDTO(RequestEditing requestEditing) {
        return new RequestEditingOutputDTO(
                requestEditing.getId(),
                requestEditing.getEstimatedCompletionDate(),
                requestEditing.getDescription(),
                requestEditing.getFullCameraName(),
                requestEditing.getSensorSize(),
                requestEditing.getKitLens(),
                requestEditing.getViewFinder(),
                requestEditing.getNativeISO(),
                requestEditing.getStatus()
        );
    }
}
