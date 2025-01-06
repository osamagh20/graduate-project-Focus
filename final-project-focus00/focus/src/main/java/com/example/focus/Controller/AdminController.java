package com.example.focus.Controller;

import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.Service.EmailService;
import lombok.RequiredArgsConstructor;
import com.example.focus.Service.StudioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/focus/admin")
public class AdminController {
    private final StudioService studioService;
    private final EmailService emailService;

    @PutMapping("/activate-studio/{admin_id}/{studio_id}")
    public ResponseEntity activateStudio(@PathVariable Integer admin_id, @PathVariable Integer studio_id){
        studioService.activateStudio(admin_id,studio_id);
        return ResponseEntity.status(200).body(new ApiResponse("admin activate studio successfully"));
    }

    @PutMapping("/reject-studio/{admin_id}/{studio_id}")
    public ResponseEntity rejectStudio(@PathVariable Integer admin_id,@PathVariable Integer studio_id){
        studioService.rejectStudio(admin_id,studio_id);
        return ResponseEntity.status(200).body(new ApiResponse("admin reject studio successfully"));
    }

//    @PostMapping("/send")
//    public ResponseEntity send(){
//        emailService.sendEmail("osamahghamdi62@gmail.com","Focus Team","Hi osama");
//        return ResponseEntity.status(200).body("sent");
//    }

}
