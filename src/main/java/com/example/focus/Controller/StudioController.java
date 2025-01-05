package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.*;
import com.example.focus.Service.StudioService;
import com.example.focus.Service.PhotographerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/focus/studio")
public class StudioController {
    private final StudioService studioService;
    private final PhotographerService photographerService;

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


    @PutMapping("/upload-image/{id}")
    public ResponseEntity updateProfile(@PathVariable Integer id, @RequestParam("file") MultipartFile file){
        try {
            studioService.UploadImage(id,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("updated successful."));
    }

    @GetMapping("/get-studio-by-city/{photographer_id}/{city}")
    public ResponseEntity getStudioByCity(@PathVariable Integer photographer_id,@PathVariable String city){
        List<StudioDTOPhotographer> studioDTOS = studioService.getStudiosByCity(photographer_id,city);
        return ResponseEntity.status(200).body(studioDTOS);
    }

    @GetMapping("/get-specific-studio/{photographer_id}/{studio_id}")
    public ResponseEntity getSpecificStudio(@PathVariable Integer photographer_id,@PathVariable Integer studio_id){
        StudioDTOPhotographer studioDTO = studioService.getSpecificStudio(photographer_id,studio_id);
        return ResponseEntity.status(200).body(studioDTO);
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
