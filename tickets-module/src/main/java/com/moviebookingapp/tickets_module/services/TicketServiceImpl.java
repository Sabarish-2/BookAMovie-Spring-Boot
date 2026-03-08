package com.moviebookingapp.tickets_module.services;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;
import com.moviebookingapp.tickets_module.entities.Ticket;
import com.moviebookingapp.tickets_module.exception.TicketNotFoundException;
import com.moviebookingapp.tickets_module.mappers.TicketMapper;
import com.moviebookingapp.tickets_module.repositories.TicketRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing ticket-related operations.
 * This class provides methods to add, retrieve, and manage tickets.
 */
@Service
public class TicketServiceImpl implements TicketService {

    /**
     * Repository for accessing ticket data.
     */
    private final TicketRepository ticketRepository;

    /**
     * Mapper for converting between DTOs and entities.
     */
    private final TicketMapper mapper;

    /**
     * Constructor for TicketServiceImpl.
     *
     * @param ticketRepository Repository for accessing ticket data.
     * @param mapper           Mapper for converting between DTOs and entities.
     */
    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
    }

    /**
     * Adds a new ticket to the system.
     *
     * @param ticketDTO Data transfer object containing ticket details.
     * @return The added ticket as a DTO.
     */
    @Override
    public TicketDTO addTicket(TicketDTO ticketDTO) {
        Ticket newTicket = mapper.map(ticketDTO);
        Ticket savedTicket = ticketRepository.save(newTicket);
        return mapper.map(savedTicket);
    }

    /**
     * Retrieves all tickets in the system.
     *
     * @return A list of all tickets as DTOs.
     * @throws TicketNotFoundException if no tickets are found.
     */
    @Override
    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException();
        }
        return tickets.stream().map(mapper::map).toList();
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param ticketID The ID of the ticket to retrieve.
     * @return The ticket as a DTO.
     * @throws TicketNotFoundException if the ticket is not found.
     */
    @Override
    @Cacheable("ticket")
    public TicketDTO getTicketByID(Long ticketID) {
        Ticket ticket = ticketRepository.findById(ticketID)
                .orElseThrow(() -> new TicketNotFoundException(ticketID));
        return mapper.map(ticket);
    }

    /**
     * Retrieves all tickets for a specific movie in a theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return A list of tickets for the specified movie and theatre.
     */
    @Override
    public List<TicketDTO> getTicketsForMovieInTheatre(String movieName, String theatreName) {
        List<Ticket> tickets = ticketRepository.findAllByMovieNameAndTheatreName(movieName, theatreName);
        return tickets.stream()
                .map(mapper::map)
                .toList();
    }

    /**
     * Retrieves all tickets booked by a specific user.
     *
     * @param userID The ID of the user.
     * @return A list of tickets booked by the user.
     * @throws TicketNotFoundException if no tickets are found for the user.
     */
    @Override
    public List<TicketDTO> getTicketsForUser(String userID) {
        List<Ticket> tickets = ticketRepository.findAllByUserID(userID);
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException(userID);
        }
        return tickets.stream().map(mapper::map).toList();
    }

    /**
     * Calculates the total number of booked tickets for a specific movie in a
     * theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return The total number of booked tickets.
     */
    @Override
    public long getBookedTickets(String movieName, String theatreName) {
        List<TicketDTO> tickets = getTicketsForMovieInTheatre(movieName, theatreName);
        return tickets.stream()
                .mapToInt(TicketDTO::getQuantity)
                .sum();
    }

    /**
     * Deletes all tickets for a specific movie in a theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     */
    @Override
    public void deleteTicketsForMovieInTheatre(String movieName, String theatreName) {
        List<TicketDTO> tickets = getTicketsForMovieInTheatre(movieName, theatreName);
        ticketRepository.deleteAllByIdInBatch(tickets.stream().map(TicketDTO::getTicketID).toList());
    }
}
