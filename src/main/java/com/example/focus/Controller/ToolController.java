package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.RentToolsDTOIn;
import com.example.focus.DTO.ToolDTO;
import com.example.focus.DTO.ToolDTOIn;
import com.example.focus.Model.Tool;
import com.example.focus.Service.PhotographerService;
import com.example.focus.Service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/focus/tool")
@RequiredArgsConstructor
public class ToolController {
    private final ToolService toolService;
    private final PhotographerService photographerService;

    @GetMapping("/get-all")
    public ResponseEntity getAllTools() {
        List<ToolDTO> tools = toolService.getAllTools();
        return ResponseEntity.status(200).body(tools);
    }

    @PostMapping("/add-tool/{photographer_id}")
    public ResponseEntity addTool(@PathVariable Integer photographer_id, @ModelAttribute  ToolDTOIn toolDTOIn,@RequestParam("file") MultipartFile file ) {
        try {
            toolService.addTool(photographer_id,toolDTOIn,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("Tool added successfully"));
    }

    // need to media file
    @PutMapping("/update-tool/{photographer_id}/{tool_id}")
    public ResponseEntity updateTool(@PathVariable Integer photographer_id,@PathVariable Integer tool_id,  @ModelAttribute  ToolDTOIn toolDTOIn,@RequestParam("file") MultipartFile file) {
        try {
            toolService.updateTool(photographer_id,tool_id,toolDTOIn,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("Tool updated successfully"));
    }

    @DeleteMapping("/delete-tool/photographerid/{photographer_id}/toolid/{tool_id}")
    public ResponseEntity<ApiResponse> deleteTool(@PathVariable Integer photographer_id,@PathVariable Integer tool_id) {
        toolService.deleteTool(photographer_id,tool_id);
        return ResponseEntity.status(200).body(new ApiResponse("Tool deleted successfully"));
    }

    @PostMapping("/rent-tool/{photographer_id}/{tool_id}")
    public ResponseEntity rentToolRequest(@PathVariable Integer photographer_id,@PathVariable Integer tool_id,@RequestBody @Valid RentToolsDTOIn rentToolsDTOIn){
        photographerService.rentToolRequest(photographer_id,tool_id,rentToolsDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Tool rent request success"));
    }

    @GetMapping("/get-tools-by-rental-number/{numberOfRental}")
    public ResponseEntity getToolsByNumberOfRental(@PathVariable Integer numberOfRental) {
        List<ToolDTO> tools = toolService.getToolsByNumberOfRental(numberOfRental);
        return ResponseEntity.status(200).body(tools);
    }

    @GetMapping("/get-tools-by-rental-number-or-above/{numberOfRental}")
    public ResponseEntity getToolsByNumberOfRentalOrAbove(@PathVariable Integer numberOfRental) {
        List<ToolDTO> tools = toolService.getToolsByNumberOfRentalOrAbove(numberOfRental);
        return ResponseEntity.status(200).body(tools);
    }

    @GetMapping("/get-tools-by-rental-number-or-below/{numberOfRental}")
    public ResponseEntity getToolsByNumberOfRentalOrBelow(@PathVariable Integer numberOfRental) {
        List<ToolDTO> tools = toolService.getToolsByNumberOfRentalOrBelow(numberOfRental);
        return ResponseEntity.status(200).body(tools);
    }

    @GetMapping("/get-my-tools/{photographer_id}")
    public ResponseEntity getMyTools(@PathVariable Integer photographer_id) {
        List<ToolDTO> tools = toolService.getMyTools(photographer_id);
        return ResponseEntity.status(200).body(tools);
    }

    @GetMapping("/get-photographer-tools/{photographer_id}/{photographer_id2}")
    public ResponseEntity getPhotographerTools(@PathVariable Integer photographer_id,@PathVariable Integer photographer_id2) {
        List<ToolDTO> tools = toolService.getPhotographerTools(photographer_id,photographer_id2);
        return ResponseEntity.status(200).body(tools);
    }
}