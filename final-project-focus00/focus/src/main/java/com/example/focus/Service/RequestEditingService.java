package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.DTO.RequestEditingInputDTO;
import com.example.focus.DTO.RequestEditingOutputDTO;
import com.example.focus.Model.RequestEditing;
import com.example.focus.Model.Editor;
import com.example.focus.Repository.RequestEditingRepository;
import com.example.focus.Repository.EditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestEditingService {

    private final RequestEditingRepository requestEditingRepository;
    private final EditorRepository editorRepository;

    public List<RequestEditingOutputDTO> getAllRequests() {
        List<RequestEditing> requests = requestEditingRepository.findAll();
        List<RequestEditingOutputDTO> requestDTOs = new ArrayList<>();
        for (RequestEditing request : requests) {
            requestDTOs.add(convertToDTO(request));
        }
        return requestDTOs;
    }

    public RequestEditingOutputDTO getRequestById(Integer id) {
        RequestEditing request = requestEditingRepository.findById(id).orElse(null);
        if (request == null) {
            throw new ApiException("Request not found");
        }
        return convertToDTO(request);
    }

    public RequestEditingOutputDTO createRequest(RequestEditingInputDTO requestInput) {
        Editor editor = editorRepository.findById(requestInput.getEditorId())
                .orElseThrow(() -> new ApiException("Editor not found"));

        RequestEditing request = new RequestEditing();
        request.setEstimatedCompletionDate(requestInput.getEstimatedCompletionDate());
        request.setDescription(requestInput.getDescription());
        request.setFullCameraName(requestInput.getFullCameraName());
        request.setSensorSize(requestInput.getSensorSize());
        request.setKitLens(requestInput.getKitLens());
        request.setViewFinder(requestInput.getViewFinder());
        request.setNativeISO(requestInput.getNativeISO());
        request.setStatus("Pending");
        request.setEditor(editor);

        return convertToDTO(requestEditingRepository.save(request));
    }

    public RequestEditingOutputDTO updateRequest(Integer id, RequestEditingInputDTO requestInput) {
        RequestEditing request = requestEditingRepository.findById(id).orElse(null);
        if (request == null) {
            throw new ApiException("Request not found");
        }

        request.setEstimatedCompletionDate(requestInput.getEstimatedCompletionDate());
        request.setDescription(requestInput.getDescription());
        request.setFullCameraName(requestInput.getFullCameraName());
        request.setSensorSize(requestInput.getSensorSize());
        request.setKitLens(requestInput.getKitLens());
        request.setViewFinder(requestInput.getViewFinder());
        request.setNativeISO(requestInput.getNativeISO());
        request.setStatus(requestInput.getStatus());

        return convertToDTO(requestEditingRepository.save(request));
    }

    public void deleteRequest(Integer id) {
        RequestEditing request = requestEditingRepository.findById(id).orElse(null);
        if (request == null) {
            throw new ApiException("Request not found");
        }
        requestEditingRepository.delete(request);
    }

    public List<RequestEditingOutputDTO> getRequestsByEditorId(Integer editorId) {
        Editor editor = editorRepository.findById(editorId).orElse(null);
        if (editor == null) {
            throw new ApiException("Editor not found");
        }

        List<RequestEditing> requests = requestEditingRepository.findRequestEditingsByEditor_Id(editorId);
        List<RequestEditingOutputDTO> requestDTOs = new ArrayList<>();
        for (RequestEditing request : requests) {
            requestDTOs.add(convertToDTO(request));
        }
        return requestDTOs;
    }

    private RequestEditingOutputDTO convertToDTO(RequestEditing request) {
        RequestEditingOutputDTO dto = new RequestEditingOutputDTO();
        dto.setId(request.getId());
        dto.setEstimatedCompletionDate(request.getEstimatedCompletionDate());
        dto.setDescription(request.getDescription());
        dto.setFullCameraName(request.getFullCameraName());
        dto.setSensorSize(request.getSensorSize());
        dto.setKitLens(request.getKitLens());
        dto.setViewFinder(request.getViewFinder());
        dto.setNativeISO(request.getNativeISO());
        dto.setEditorId(request.getEditor().getId());
        dto.setStatus(request.getStatus());
        return dto;
    }
}
