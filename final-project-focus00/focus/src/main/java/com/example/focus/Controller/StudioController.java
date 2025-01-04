package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.*;
import com.example.focus.Service.StudioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/focus/studio")
public class StudioController {
    private final StudioService studioService;

    @GetMapping("/get-all")
    public ResponseEntity getAllStudios(){
        List<StudioDTO> studios = studioService.getAllStudios();
        return ResponseEntity.status(200).body(studios);
    }

    @PostMapping("/register")
    public ResponseEntity registerStudio(@RequestBody @Valid StudioDTOIn studio){
        studioService.registerStudio(studio);
        return ResponseEntity.status(200).body(new ApiResponse("studio registered successfully"));
    }

    @PutMapping("/studio-status/{admin_id}/{studio_id}/{status}")
    public ResponseEntity activateStudio(@PathVariable Integer admin_id,@PathVariable Integer studio_id,@PathVariable String status){
        studioService.activateStudio(admin_id,studio_id,status);
        return ResponseEntity.status(200).body(new ApiResponse("admin modify status of studio success"));
    }



//    @PostMapping("/add-space/{studio_id}")
//    public ResponseEntity addSpace(@PathVariable Integer studio_id,@RequestBody @Valid SpaceDTOIn spaceDTOIn){
//        studioService.addSpace(studio_id,spaceDTOIn);
//        return ResponseEntity.status(200).body(new ApiResponse("sapace added successfully"));
//    }



//    @PutMapping("/update-space/{studio_id}")
//    public ResponseEntity updateSpace(@PathVariable Integer studio_id,@RequestBody @Valid SpaceDTOIn spaceDTOIn){
//        studioService.updateSpace(studio_id,spaceDTOIn);
//        return ResponseEntity.status(200).body(new ApiResponse("space updated successfully"));
//    }
//
//    @DeleteMapping("/delete-space/{studio_id}/{space_name}")
//    public ResponseEntity deleteSpace(@PathVariable Integer studio_id,@PathVariable String space_name){
//        studioService.deleteSpace(studio_id,space_name);
//        return ResponseEntity.status(200).body(new ApiResponse("space deleted successfully"));
//    }
//
//    @GetMapping("/get-all-requests")
//    public ResponseEntity getAllSpaceRequests(){
//        List<RentalStudioRequestDTO> rentalStudioRequestDTOS = studioService.getSpaces();
//        return ResponseEntity.status(200).body(rentalStudioRequestDTOS);
//    }
//
//    @PutMapping("/accept-or-reject/studio_id/{studio_id}/request_id/{request_id}/response/{response}")
//    public ResponseEntity requestResponse(@PathVariable Integer studio_id,@PathVariable Integer request_id,@PathVariable String response){
//        studioService.acceptOrRejectRequest(studio_id,request_id,response);
//        return ResponseEntity.status(200).body(new ApiResponse("your request is "+response+"ed"));
//    }

}
