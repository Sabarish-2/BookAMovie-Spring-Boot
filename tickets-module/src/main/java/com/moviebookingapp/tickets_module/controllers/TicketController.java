package com.moviebookingapp.tickets_module.controllers;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@ApiResponse(responseCode = "401", description = "Authentication Error")
@ApiResponse(responseCode = "500", description = "Unexpected Error Internally")
public interface TicketController {


    @Operation(summary = "Create A New Ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket Created Successfully"),
            @ApiResponse(responseCode = "400", description = "Validation Error in Ticket Details Provided"),
    })
    ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO);

    @Operation(summary = "Retrieve All Tickets", method = "Ticket Controller")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Tickets Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "No Tickets Retrieved")
    })
    ResponseEntity<List<TicketDTO>> viewAllTickets();

    @Operation(summary = "Retrieve Tickets With Ticket ID", method = "Ticket Controller")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket With Given ID Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket With Given ID Not Found")
    })
    ResponseEntity<TicketDTO> getTicketByID(@PathVariable Long ticketID);

    @Operation(summary = "Retrieve All Tickets For A Movie In A Theatre", method = "Ticket Controller")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Tickets Of Given Movie In Given Theatre Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "No Tickets Retrieved")
    })
    ResponseEntity<List<TicketDTO>> getTicketsForTicketInTheatre(@PathVariable String movieName, @PathVariable String theatreName);

    @Operation(summary = "Retrieve All Tickets For A User", method = "Ticket Controller")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Tickets Of Given User Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "No Tickets Retrieved")
    })
    ResponseEntity<List<TicketDTO>> getTicketsForUser(@PathVariable String userID);

    @Operation(summary = "Retrieve Number Of Tickets Booked For A Movie In A Theatre", method = "Ticket Controller")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tickets Booked Of Given Movie In Given Theatre Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "No Tickets Found")
    })
    ResponseEntity<Long> getBookedTickets(@PathVariable String movieName, @PathVariable String theatreName);


    @Operation(summary = "Delete An Existing Ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket Deleted Successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket Not Found"),
//            @ApiResponse(responseCode = "400", description = "Validation Error in Ticket Details Provided")
    })
    ResponseEntity<String> deleteTicketsForTicketInTheatre(@PathVariable String movieName, @PathVariable String theatreName);
}
