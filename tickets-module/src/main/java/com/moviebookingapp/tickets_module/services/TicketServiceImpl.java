package com.moviebookingapp.tickets_module.services;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;
import com.moviebookingapp.tickets_module.entities.Ticket;
import com.moviebookingapp.tickets_module.exception.TicketNotFoundException;
import com.moviebookingapp.tickets_module.mappers.TicketMapper;
import com.moviebookingapp.tickets_module.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
    }

    @Override
    public TicketDTO addTicket(TicketDTO ticketDTO) {
        Ticket newTicket = mapper.map(ticketDTO);
        Ticket savedTicket = ticketRepository.save(newTicket);
        return mapper.map(savedTicket);
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException();
        }
        return tickets.stream().map(mapper::map).toList();
    }

    @Override
    public TicketDTO getTicketByID(Long ticketID) {
        Ticket ticket = ticketRepository.findById(ticketID)
                .orElseThrow(() -> new TicketNotFoundException(ticketID));
        return mapper.map(ticket);
    }

    @Override
    public List<TicketDTO> getTicketsForMovieInTheatre(String movieName, String theatreName) {
        List<Ticket> tickets = ticketRepository.findAllByMovieNameAndTheatreName(movieName, theatreName);
//        if (tickets.isEmpty()) {
//            throw new TicketNotFoundException(movieName, theatreName);
//        }
        return tickets.stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<TicketDTO> getTicketsForUser(String userID) {
        List<Ticket> tickets = ticketRepository.findAllByUserID(userID);
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException(userID);
        }
        return tickets.stream().map(mapper::map).toList();
    }

    @Override
    public long getBookedTickets(String movieName, String theatreName) {
        List<TicketDTO> tickets = getTicketsForMovieInTheatre(movieName, theatreName);
//        List<Integer> bookedTickets = tickets.stream().reduce(ticketDTO -> ticketDTO.getQuantity()).toList();
//        return bookedTickets.stream().reduce((ticket, ticket2) -> ticket.getQuantity() + ticket2.getQuantity());
//        return bookedTickets.stream()
//                .reduce(0, Integer::sum);
//        return tickets.stream()
//                .reduce((ticketDTO, ticketDTO2) -> {
//                    ticketDTO.setQuantity(ticketDTO.getQuantity() + ticketDTO2.getQuantity());
//                    return ticketDTO;
//                }).get().getQuantity();
        return tickets.stream()
                .mapToInt(TicketDTO::getQuantity)
                .sum();
    }


    @Override
    public void deleteTicketsForMovieInTheatre(String movieName, String theatreName) {
        List<TicketDTO> tickets = getTicketsForMovieInTheatre(movieName, theatreName);
        ticketRepository.deleteAllByIdInBatch(tickets.stream().map(TicketDTO::getTicketID).toList());
    }
}
