package com.example.focus.Controller;


import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.ProfileDTOin;
import com.example.focus.Service.ProfileEditorService;
import com.example.focus.Service.ProfilePhotographerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/focus/editor/profile")
@RequiredArgsConstructor
public class ProfileEditorController {

    private final ProfileEditorService profileEditorService;


    @GetMapping("/get-all")
    public ResponseEntity getAllProfileEditor(){
        return ResponseEntity.status(200).body(profileEditorService.getAllProfiles()) ;
    }

    @GetMapping("/get-my-profile/{id}")
    public ResponseEntity getMyProfileEditor(@PathVariable Integer id){
        return ResponseEntity.status(200).body(profileEditorService.getMyProfile(id));
    }

    @GetMapping("/get-specific-profile/{id}")
    public ResponseEntity getSpecificProfileEditor(@PathVariable Integer id){
        return ResponseEntity.status(200).body(profileEditorService.getSpecificProfile(id));
    }


    @PostMapping("/upload-media/{id}")
    public ResponseEntity uploadMedia(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            profileEditorService.uploadMediaProfile(id,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("Upload successful."));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateProfile(@PathVariable Integer id, @ModelAttribute @Valid ProfileDTOin profileDTOin, @RequestParam("file") MultipartFile file){
        try {
            profileEditorService.updateProfile(id,profileDTOin,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("updated successful."));
    }
}
