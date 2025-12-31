package com.moviebookingapp.tickets_module.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketID;

    @Column(nullable = false)
    private String userID;

    @Column(nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String theatreName;

    @Column(nullable = false)
    private String seatNumbers;

    @Min(1)
    @Column(nullable = false)
    private Integer quantity;

}