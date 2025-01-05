package com.example.focus.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OfferEditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "request_id", insertable = false, updatable = false) // Fix: Mark as non-insertable and non-updatable
    private RequestEditing requestEditing;

    @Column(nullable = false)
    private LocalDateTime offerDate;

    @Column(nullable = false)
    private BigDecimal offeredPrice;

    @Column(nullable = false)
    private LocalDateTime estimatedCompletionTime;

    @Column(nullable = false)
    private String status; // Applied, Accepted, Rejected

    @ManyToOne
    @JoinColumn(name = "editor_id", nullable = false)
    private Editor editor;
}
