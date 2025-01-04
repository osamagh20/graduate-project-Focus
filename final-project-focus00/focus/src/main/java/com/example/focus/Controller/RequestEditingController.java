package com.example.focus.Controller;

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

    @PostMapping
    public ResponseEntity createRequest(@RequestBody @Valid RequestEditingInputDTO requestEditingInputDTO) {
        RequestEditingOutputDTO createdRequest = requestEditingService.createRequest(requestEditingInputDTO);
        return ResponseEntity.status(200).body(createdRequest);
    }

    @GetMapping
    public ResponseEntity getAllRequests() {
        List<RequestEditingOutputDTO> requests = requestEditingService.getAllRequests();
        return ResponseEntity.status(200).body(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity getRequestById(@PathVariable Integer id) {
        RequestEditingOutputDTO request = requestEditingService.getRequestById(id);
        return ResponseEntity.status(200).body(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateRequest(@PathVariable Integer id, @RequestBody @Valid RequestEditingInputDTO requestEditingInputDTO) {
        RequestEditingOutputDTO updatedRequest = requestEditingService.updateRequest(id, requestEditingInputDTO);
        return ResponseEntity.status(200).body(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRequest(@PathVariable Integer id) {
        requestEditingService.deleteRequest(id);
        return ResponseEntity.status(200).body("Request deleted successfully");
    }
}
