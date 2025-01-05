package com.example.focus.Repository;

import com.example.focus.Model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media,Integer> {
    Media findMediaById(Integer id);
//
//    // إرجاع عدد الفيديوهات او الصور لمصور أو محرر معين
//    Integer countByProfilePhotographerIdAndMediaType(Integer photographerId, String mediaType);
//    Integer countByProfileEditorIdAndMediaType(Integer editorId, String mediaType);
//
//    // إرجاع الصور او الفيديوهات فقط لمصور أو محرر معين
//    List<Media> findByProfilePhotographerIdAndMediaType(Integer photographerId, String mediaType);
//    List<Media> findByProfileEditorIdAndMediaType(Integer editorId, String mediaType);
//

}