package com.example.focus.Controller;


import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.Service.ProfilePhotographerService;
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


    @PostMapping("/upload/{id}")
    public ResponseEntity uploadMedia(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            profilePhotographerService.saveMediaFile(id,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("Upload successful."));
    }
}
