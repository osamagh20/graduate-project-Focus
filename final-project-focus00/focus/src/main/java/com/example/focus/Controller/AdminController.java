package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import com.example.focus.Service.StudioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/focus/admin")
public class AdminController {
    private final StudioService studioService;

    @PutMapping("/studio-status/{admin_id}/{studio_id}/{status}")
    public ResponseEntity activateStudio(@PathVariable Integer admin_id, @PathVariable Integer studio_id, @PathVariable String status){
        studioService.activateStudio(admin_id,studio_id);
        return ResponseEntity.status(200).body(new ApiResponse("admin modify status of studio success"));
    }

    @PutMapping("/reject-studio/{admin_id}/{studio_id}")
    public ResponseEntity rejectStudio(@PathVariable Integer admin_id,@PathVariable Integer studio_id){
        studioService.rejectStudio(admin_id,studio_id);
        return ResponseEntity.status(200).body(new ApiResponse("admin rejected studio success"));
    }

}
