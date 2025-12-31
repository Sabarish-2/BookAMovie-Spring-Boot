package com.moviebookingapp.tickets_module.services;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO addTicket(TicketDTO ticketDTO);
    List<TicketDTO> getAllTickets();
    TicketDTO getTicketByID(Long ticketID);
    List<TicketDTO> getTicketsForMovieInTheatre(String movieName, String theatreName);
    List<TicketDTO> getTicketsForUser(String userID);
    long getBookedTickets(String movieName, String theatreName);
    void deleteTicketsForMovieInTheatre(String movieName, String theatreName);
}
