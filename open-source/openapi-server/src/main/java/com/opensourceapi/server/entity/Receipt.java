package com.opensourceapi.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String merchantName;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    private BigDecimal taxAmount;

    private LocalDate date;

    private String category;

    private String imageUrl;

    @Column(length = 1000)
    private String notes;
}
