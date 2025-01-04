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
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer requestId;

    @Column(nullable = false)
    private LocalDateTime expectedFinishDate;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String status; // Applied, Accepted, Rejected
}
