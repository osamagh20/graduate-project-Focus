package com.example.focus.Controller;


import com.example.focus.ApiResponse.ApiResponse;
import com.example.focus.Model.Media;
import com.example.focus.Service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/focus/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
//
//   //  إرجاع عدد الفيديوهات لمصور معين
//    @GetMapping("/photographer/count-video/{photographerId}")
//    public ResponseEntity<Integer> getVideoCountByPhotographer(@PathVariable Integer photographerId) {
//        Integer count = mediaService.getVideoCountByPhotographer(photographerId);
//        return ResponseEntity.ok(count);
//    }
//
//    // إرجاع عدد الصور لمصور معين
//    @GetMapping("/photographer/count-image/{photographerId}")
//    public ResponseEntity getImageCountByPhotographer(@PathVariable Integer photographerId) {
//        Integer count = mediaService.getImageCountByPhotographer(photographerId);
//        return ResponseEntity.ok(count);
//    }
//
//    // إرجاع الفيديوهات فقط لمصور معين
//    @GetMapping("/photographer/get-video/{photographerId}")
//    public ResponseEntity getVideosByPhotographer(@PathVariable Integer photographerId) {
//        List<Media> videos = mediaService.getVideosByPhotographer(photographerId);
//        return ResponseEntity.ok(videos);
//    }
//
//    // إرجاع الصور فقط لمصور معين
//    @GetMapping("/photographer/{photographerId}/images")
//    public ResponseEntity getImagesByPhotographer(@PathVariable Integer photographerId) {
//        List<Media> images = mediaService.getImagesByPhotographer(photographerId);
//        return ResponseEntity.ok(images);
//    }
//
//
//
//    //**************************Editor
//    // إرجاع عدد الفيديوهات editor معين
//    @GetMapping("/editor/count-video/{editorId}")
//    public ResponseEntity<Integer> getVideoCountByEditor(@PathVariable Integer editorId) {
//        Integer count = mediaService.getVideoCountByEditor(editorId);
//        return ResponseEntity.ok(count);
//    }
//
//    // إرجاع عدد الصور editor معين
//    @GetMapping("/editor/count-image/{editorId}")
//    public ResponseEntity getImageCountByEditor(@PathVariable Integer editorId) {
//        Integer count = mediaService.getImageCountByEditor(editorId);
//        return ResponseEntity.ok(count);
//    }
//
//    // إرجاع الفيديوهات فقط editor معين
//    @GetMapping("/editor/get-videos/{editorId}")
//    public ResponseEntity getVideosByEditor(@PathVariable Integer editorId) {
//        List<Media> videos = mediaService.getVideosByEditor(editorId);
//        return ResponseEntity.ok(videos);
//    }
//
//    // إرجاع الصور فقط editor معين
//    @GetMapping("/editor/get-images/{editorid}/")
//    public ResponseEntity getImagesByEditor(@PathVariable Integer editorid) {
//        List<Media> images = mediaService.getImagesByEditor(editorid);
//        return ResponseEntity.ok(images);
//    }



}
