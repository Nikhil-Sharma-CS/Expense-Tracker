package org.example.Expense.Tracker.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prodId;

    private String prodTitle;

    private String prodDesc;

    private Long prodPrice;

    private LocalDate prodDate;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    User user;
}
