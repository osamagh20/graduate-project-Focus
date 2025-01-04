package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.PhotographerDTOin;
import com.example.focus.DTO.StudioDTO;
import com.example.focus.DTO.ToolDTO;
import com.example.focus.Service.PhotographerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/focus/photographer")
@RequiredArgsConstructor
public class PhotographerController {

    private final PhotographerService photographerService;

    @GetMapping("/get-all")
    public ResponseEntity getAllPhotographer(){
        return ResponseEntity.status(200).body(photographerService.getAllPhotographers());
    }

    @PostMapping("/register")
    public ResponseEntity PhotographerRegistration(@RequestBody @Valid PhotographerDTOin photographer){
        photographerService.PhotographerRegistration(photographer);
        return ResponseEntity.status(200).body(new ApiResponse("photographer added"));
    }

    @PutMapping("/update-photographer/{id}")
    public ResponseEntity updatePhotographer(@PathVariable Integer id, @RequestBody @Valid PhotographerDTOin photographer){
        photographerService.updatePhotographer(id,photographer);
        return ResponseEntity.status(200).body(new ApiResponse("photographer updated"));
    }

    @DeleteMapping("/delete-photographer/{id}")
    public ResponseEntity deletePhotographer(@PathVariable Integer id){
        photographerService.deletePhotographer(id);
        return ResponseEntity.status(200).body(new ApiResponse("photographer deleted"));
    }

    @GetMapping("/get-my-rent-tools/{photographer_id}")
    public ResponseEntity viewMyRentTools(@PathVariable Integer photographer_id){
        List<ToolDTO> rentTools = photographerService.viewMyRentTools(photographer_id);
        return ResponseEntity.status(200).body(rentTools);
    }

    @GetMapping("/get-my-rental-tools/{photographer_id}")
    public ResponseEntity viewRentalTools(@PathVariable Integer photographer_id){
        List<ToolDTO> rentTools = photographerService.viewRentalTools(photographer_id);
        return ResponseEntity.status(200).body(rentTools);
    }


    @GetMapping("/get-studio-by-city/{photographer_id}/{city}")
    public ResponseEntity getStudioByCity(@PathVariable Integer photographer_id,@PathVariable String city){
        List<StudioDTO> studioDTOS = photographerService.getStudiosByCity(photographer_id,city);
        return ResponseEntity.status(200).body(studioDTOS);
    }

    @GetMapping("/get-specific-studio")
    public ResponseEntity getSpecificStudio(@PathVariable Integer photographer_id,@PathVariable Integer studio_id){
        StudioDTO studioDTO = photographerService.getSpecificStudio(photographer_id,studio_id);
        return ResponseEntity.status(200).body(studioDTO);
    }


}