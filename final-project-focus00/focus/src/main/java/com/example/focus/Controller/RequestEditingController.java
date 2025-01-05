package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.RequestEditingInputDTO;
import com.example.focus.DTO.RequestEditingOutputDTO;
import com.example.focus.Service.RequestEditingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/focus/request-editing")
@RequiredArgsConstructor
public class RequestEditingController {

    private final RequestEditingService requestEditingService;

    @GetMapping("/get-all")
    public ResponseEntity getAllRequests() {
        List<RequestEditingOutputDTO> requests = requestEditingService.getAllRequests();
        return ResponseEntity.status(200).body(requests);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getRequestById(@PathVariable Integer id) {
        RequestEditingOutputDTO request = requestEditingService.getRequestById(id);
        return ResponseEntity.status(200).body(request);
    }

    @GetMapping("/get-by-editor/{editorId}")
    public ResponseEntity getRequestsByEditor(@PathVariable Integer editorId) {
        List<RequestEditingOutputDTO> requests = requestEditingService.getRequestsByEditorId(editorId);
        return ResponseEntity.status(200).body(requests);
    }

    @PostMapping("/create")
    public ResponseEntity createRequest(@RequestBody @Valid RequestEditingInputDTO requestInput) {
        RequestEditingOutputDTO createdRequest = requestEditingService.createRequest(requestInput);
        return ResponseEntity.status(200).body(createdRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRequest(@PathVariable Integer id, @RequestBody @Valid RequestEditingInputDTO requestInput) {
        RequestEditingOutputDTO updatedRequest = requestEditingService.updateRequest(id, requestInput);
        return ResponseEntity.status(200).body(updatedRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRequest(@PathVariable Integer id) {
        requestEditingService.deleteRequest(id);
        return ResponseEntity.status(200).body(new ApiResponse("Request deleted successfully"));
    }
}
