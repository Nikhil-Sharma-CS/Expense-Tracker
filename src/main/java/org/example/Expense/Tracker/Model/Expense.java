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
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expId;

    private Long expAmount;

    private LocalDate expStartDate;

    private LocalDate expEndDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
