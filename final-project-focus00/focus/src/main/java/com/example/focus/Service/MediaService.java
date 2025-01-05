package com.example.focus.Service;

import com.example.focus.ApiResponse.ApiException;
import com.example.focus.Model.Media;
import com.example.focus.Model.ProfileEditor;
import com.example.focus.Model.ProfilePhotographer;
import com.example.focus.Repository.MediaRepository;
import com.example.focus.Repository.PhotographerRepository;
import com.example.focus.Repository.ProfileEditorRepository;
import com.example.focus.Repository.ProfilePhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaService {
private final ProfilePhotographerRepository profilePhotographerRepository;
private final ProfileEditorRepository profileEditorRepository;


private final MediaRepository mediaRepository;

//
//    // إرجاع عدد الفيديوهات لمصور معين
//    public Integer getVideoCountByPhotographer(Integer photographerId) {
//        return mediaRepository.countByProfilePhotographerIdAndMediaType(photographerId, "video");
//    }
//
//    // إرجاع عدد الصور لمصور معين
//    public Integer getImageCountByPhotographer(Integer photographerId) {
//        return mediaRepository.countByProfilePhotographerIdAndMediaType(photographerId, "image");
//    }
//
//    // إرجاع الفيديوهات فقط لمصور معين
//    public List<Media> getVideosByPhotographer(Integer editorid) {
//        return mediaRepository.findByProfilePhotographerIdAndMediaType(editorid, "video");
//    }
//
//    // إرجاع الصور فقط لمصور معين
//    public List<Media> getImagesByPhotographer(Integer photographerId) {
//        return mediaRepository.findByProfilePhotographerIdAndMediaType(photographerId, "image");
//    }
//
//
//    //**************************Editor
//
//    // إرجاع عدد الفيديوهات editor معين
//    public Integer getVideoCountByEditor(Integer editorid) {
//        return mediaRepository.countByProfileEditorIdAndMediaType(editorid, "video");
//    }
//
//    // إرجاع عدد الصور editor معين
//    public Integer getImageCountByEditor(Integer editorid) {
//        return mediaRepository.countByProfileEditorIdAndMediaType(editorid, "image");
//    }
//
//    // إرجاع الفيديوهات فقط editor معين
//    public List<Media> getVideosByEditor(Integer editorid) {
//        return mediaRepository.findByProfileEditorIdAndMediaType(editorid, "video");
//    }
//
//    // إرجاع الصور فقط editor معين
//    public List<Media> getImagesByEditor(Integer editorid) {
//        return mediaRepository.findByProfileEditorIdAndMediaType(editorid, "image");
//    }



}
