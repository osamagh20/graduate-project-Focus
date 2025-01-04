package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.DTO.SpaceDTO;
import com.example.focus.DTO.SpaceDTOIn;
import com.example.focus.Service.SpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity createSpace(@PathVariable Integer studioid , @RequestBody @Valid SpaceDTOIn spaceDTOin){
        spaceService.createSpace(studioid,spaceDTOin);
        return ResponseEntity.status(200).body(new ApiResponse("created successfully"));
    }

}
