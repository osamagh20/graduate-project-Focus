package com.example.focus.Repository;

import com.example.focus.Model.OfferEditing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferEditingRepository extends JpaRepository<OfferEditing, Integer> {

    List<OfferEditing> findByStatus(String status);

    List<OfferEditing> findByRequestEditingId(Integer requestId);
}
