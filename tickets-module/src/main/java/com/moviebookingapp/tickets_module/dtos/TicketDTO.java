package com.moviebookingapp.tickets_module.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    @Min(value = 1, message = "{com.moviebookingapp.tickets_module.dtos.number.invalid}")
    private Long ticketID;

    @NotBlank(message = "{com.moviebookingapp.tickets_module.dtos.empty}")
    private String userID;

    @NotBlank(message = "{com.moviebookingapp.tickets_module.dtos.empty}")
    private String movieName;

    @NotBlank(message = "{com.moviebookingapp.tickets_module.dtos.empty}")
    private String theatreName;

    @NotBlank(message = "{com.moviebookingapp.tickets_module.dtos.empty}")
    private String seatNumbers;

    @Min(value = 1, message = "{com.moviebookingapp.tickets_module.dtos.number.invalid}")
    @NotNull(message = "{com.moviebookingapp.tickets_module.dtos.empty}")
    private Integer quantity;

}
