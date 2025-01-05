package com.example.focus.Controller;


import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.ProfileDTO;
import com.example.focus.DTO.ProfileDTOin;
import com.example.focus.DTO.ToolDTOIn;
import com.example.focus.Service.ProfilePhotographerService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/focus/photographer/profile")
@RequiredArgsConstructor
public class ProfilePhotographerController {
    private final ProfilePhotographerService profilePhotographerService;


    @GetMapping("/get-all")
    public  ResponseEntity getAllProfilePhotographer(){
       return ResponseEntity.status(200).body(profilePhotographerService.getAllProfiles()) ;
    }

    @GetMapping("/get-my-profile/{id}")
    public ResponseEntity getMyProfilePhotographer(@PathVariable Integer id){
        return ResponseEntity.status(200).body(profilePhotographerService.getMyProfile(id));
    }

    @GetMapping("/get-specific-profile/{id}")
    public ResponseEntity getSpecificProfilePhotographer(@PathVariable Integer user1, @PathVariable Integer user2){
        return ResponseEntity.status(200).body(profilePhotographerService.getSpecificProfile(user1,user2));
    }


    @PostMapping("/upload-media/{id}")
    public ResponseEntity uploadMedia(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            profilePhotographerService.uploadMedia(id,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("Upload successful."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProfile(@PathVariable Integer id, @ModelAttribute @Valid ProfileDTOin profileDTOin, @RequestParam("file") MultipartFile file){
        try {
            profilePhotographerService.updateProfile(id,profileDTOin,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("updated successful."));
    }
}
