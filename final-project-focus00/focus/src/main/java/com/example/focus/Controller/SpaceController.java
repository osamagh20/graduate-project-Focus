package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.SpaceDTO;
import com.example.focus.DTO.SpaceDTOIn;
import com.example.focus.DTO.ToolDTOIn;
import com.example.focus.Service.SpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/focus/space")
public class SpaceController {
    private final SpaceService spaceService;

    @GetMapping("/get-all-spaces")
    public ResponseEntity getAllSpaces(){
        return ResponseEntity.status(200).body(spaceService.getAllSpaces());
    }


    @PostMapping("/create-space/{studioid}")
    public ResponseEntity createSpace(@PathVariable Integer studioid , @ModelAttribute SpaceDTOIn spaceDTOIn, @RequestParam("file") MultipartFile file){
    try {
        spaceService.createSpace(studioid,spaceDTOIn,file);

    }  catch (IOException e) {
        return ResponseEntity.status(500).body("Error occurred while uploading the file.");
    }
        return ResponseEntity.status(200).body(new ApiResponse("created successfully"));
    }


    @PutMapping("/update-my-space/{studioid}")
    public ResponseEntity UpdateSpace(@PathVariable Integer studioid , @ModelAttribute SpaceDTOIn spaceDTOIn, @RequestParam("file") MultipartFile file){
        try {
            spaceService.updateSpace(studioid,spaceDTOIn,file);

        }  catch (IOException e) {
            return ResponseEntity.status(500).body("Error occurred while uploading the file.");
        }
        return ResponseEntity.status(200).body(new ApiResponse("created successfully"));
    }

    @GetMapping("/get-specific-space/{space_id}")
    public ResponseEntity getSpaceById(@PathVariable Integer space_id){
        SpaceDTO spaceDTOS = spaceService.getSpaceById(space_id);
        return ResponseEntity.status(200).body(spaceDTOS);
    }

    @GetMapping("/get-my-spaces/{studio_id}")
    public ResponseEntity getAllMySpaces(@PathVariable Integer studio_id){
        List<SpaceDTO> spaceDTOS = spaceService.getAllMySpaces(studio_id);
        return ResponseEntity.status(200).body(spaceDTOS);
    }


    @GetMapping("/get-available-spaces/{studio_id}")
    public ResponseEntity getMyAvailableSpaces(@PathVariable Integer studio_id){
        List<SpaceDTO> spaceDTOS = spaceService.getMyAvailableSpaces(studio_id);
        return ResponseEntity.status(200).body(spaceDTOS);
    }


}
