package com.moviebookingapp.tickets_module.controllers;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;
import com.moviebookingapp.tickets_module.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller implementation for managing ticket-related operations.
 * This class handles HTTP requests for creating, retrieving, and managing
 * tickets.
 */
@RestController
public class TicketControllerImpl implements TicketController {

    /**
     * Service for handling ticket-related business logic.
     */
    private TicketService ticketService;

    /**
     * Sets the ticket service.
     *
     * @param ticketService The ticket service to set.
     */
    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Creates a new ticket.
     *
     * @param ticketDTO The ticket details to create.
     * @return A response entity containing the created ticket.
     */
    @Override
    @PostMapping("/{movieName}/add")
    public ResponseEntity<TicketDTO> createTicket(TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketService.addTicket(ticketDTO), HttpStatus.CREATED);
    }

    /**
     * Retrieves all tickets.
     *
     * @return A response entity containing a list of all tickets.
     */
    @Override
    @GetMapping("/tickets/all")
    public ResponseEntity<List<TicketDTO>> viewAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param ticketID The ID of the ticket to retrieve.
     * @return A response entity containing the ticket details.
     */
    @Override
    @GetMapping("/tickets/{ticketID}")
    public ResponseEntity<TicketDTO> getTicketByID(Long ticketID) {
        return ResponseEntity.ok(ticketService.getTicketByID(ticketID));
    }

    /**
     * Retrieves tickets for a specific movie in a specific theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return A response entity containing a list of tickets for the specified
     *         movie in the specified theatre.
     */
    @GetMapping("/tickets/{movieName}/{theatreName}")
    public ResponseEntity<List<TicketDTO>> getTicketsForMovieInTheatre(String movieName, String theatreName) {
        return ResponseEntity.ok(ticketService.getTicketsForMovieInTheatre(movieName, theatreName));
    }

    /**
     * Retrieves tickets booked by a specific user.
     *
     * @param userID The ID of the user.
     * @return A response entity containing a list of tickets booked by the user.
     */
    @Override
    @GetMapping("/tickets/user/{userID}")
    public ResponseEntity<List<TicketDTO>> getTicketsForUser(String userID) {
        return ResponseEntity.ok(ticketService.getTicketsForUser(userID));
    }

    /**
     * Retrieves the number of booked tickets for a specific movie in a specific
     * theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return A response entity containing the number of booked tickets.
     */
    @Override
    @GetMapping("/tickets/booked/{movieName}/{theatreName}")
    public ResponseEntity<Long> getBookedTickets(String movieName, String theatreName) {
        return ResponseEntity.ok(ticketService.getBookedTickets(movieName, theatreName));
    }

    /**
     * Deletes tickets for a specific movie in a specific theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return A response entity containing a success message.
     */
    @Override
    @DeleteMapping("/tickets/{movieName}/delete/{theatreName}")
    public ResponseEntity<String> deleteTicketsForMovieInTheatre(String movieName, String theatreName) {
        ticketService.deleteTicketsForMovieInTheatre(movieName, theatreName);
        return ResponseEntity
                .ok("Tickets for Movie: " + movieName + " In " + theatreName + " were Deleted Successfully!");
    }
}
