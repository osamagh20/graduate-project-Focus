package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.RentToolsDTOIn;
import com.example.focus.DTO.ToolDTOIn;
import com.example.focus.Model.Tool;
import com.example.focus.Service.PhotographerService;
import com.example.focus.Service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/focus/tool")
@RequiredArgsConstructor
public class ToolController {
    private final ToolService toolService;
    private final PhotographerService photographerService;

    //    @GetMapping("/get-all")
//    public ResponseEntity getAllTools() {
//        return ResponseEntity.status(200).body(toolService.getAllTools());
//    }
//
    @PostMapping("/add-tool/{photographer_id}")
    public ResponseEntity addTool(@PathVariable Integer photographer_id, @RequestBody @Valid ToolDTOIn toolDTOIn) {
        toolService.addTool(photographer_id,toolDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Tool added successfully"));
    }

//    @PutMapping("/update-tool/{id}")
//    public ResponseEntity updateTool(@PathVariable Integer id, @RequestBody @Valid Tool tool) {
//        toolService.updateTool(id, tool);
//        return ResponseEntity.status(200).body(new ApiResponse("Tool updated successfully"));
//    }

    @DeleteMapping("/delete-tool/photographerid{photographerid}/toolid/{toolid}")
    public ResponseEntity<ApiResponse> deleteTool(@PathVariable Integer photographerid,@PathVariable Integer toolid) {
        toolService.deleteTool(photographerid,toolid);
        return ResponseEntity.status(200).body(new ApiResponse("Tool deleted successfully"));
    }

    @PostMapping("/rent-tool/{photographer_id}/{tool_id}")
    public ResponseEntity rentToolRequest(@PathVariable Integer photographer_id,@PathVariable Integer tool_id,@RequestBody @Valid RentToolsDTOIn rentToolsDTOIn){
        photographerService.rentToolRequest(photographer_id,tool_id,rentToolsDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Tool rent request success"));
    }
}