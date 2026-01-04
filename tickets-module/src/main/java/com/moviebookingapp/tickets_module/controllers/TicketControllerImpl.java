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

@RestController
//@RequestMapping("/tickets")
public class TicketControllerImpl implements TicketController {

    private TicketService ticketService;

    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    @PostMapping("/{movieName}/add")
    public ResponseEntity<TicketDTO> createTicket(TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketService.addTicket(ticketDTO), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/tickets/all")
    public ResponseEntity<List<TicketDTO>> viewAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @Override
    @GetMapping("/tickets/{ticketID}")
    public ResponseEntity<TicketDTO> getTicketByID(Long ticketID) {
        return ResponseEntity.ok(ticketService.getTicketByID(ticketID));
    }

    @GetMapping("/tickets/{movieName}/{theatreName}")
    public ResponseEntity<List<TicketDTO>> getTicketsForMovieInTheatre(String movieName, String theatreName) {
        return ResponseEntity.ok(ticketService.getTicketsForMovieInTheatre(movieName, theatreName));
    }

    @Override
    @GetMapping("/tickets/user/{userID}")
    public ResponseEntity<List<TicketDTO>> getTicketsForUser(String userID) {
        return ResponseEntity.ok(ticketService.getTicketsForUser(userID));
    }

    @Override
    @GetMapping("/tickets/booked/{movieName}/{theatreName}")
    public ResponseEntity<Long> getBookedTickets(String movieName, String theatreName) {
        return ResponseEntity.ok(ticketService.getBookedTickets(movieName, theatreName));
    }

    @Override
    @DeleteMapping("/tickets/{movieName}/delete/{theatreName}")
    public ResponseEntity<String> deleteTicketsForMovieInTheatre(String movieName, String theatreName) {
        ticketService.deleteTicketsForMovieInTheatre(movieName, theatreName);
        return ResponseEntity.ok("Tickets for Movie: " + movieName + " In " + theatreName + " were Deleted Successfully!");
    }
}
