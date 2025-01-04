package com.example.focus.Repository;

import com.example.focus.Model.Media;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MediaRepository extends JpaRepository<Media,Integer> {
    Media findMediaById(Integer id);

}