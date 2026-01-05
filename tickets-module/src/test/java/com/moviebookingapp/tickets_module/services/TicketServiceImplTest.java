package com.moviebookingapp.tickets_module.services;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;
import com.moviebookingapp.tickets_module.entities.Ticket;
import com.moviebookingapp.tickets_module.exception.TicketNotFoundException;
import com.moviebookingapp.tickets_module.mappers.TicketMapper;
import com.moviebookingapp.tickets_module.repositories.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper mapper;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private final Long ticketId = 10L;
    private final String userId = "user-123";
    private final String movieName = "World War -I";
    private final String theatreName = "PSR";
    private final String seatNumbers = "A1,A2";
    private final int quantity = 2;

    private final Ticket ticket = new Ticket(ticketId, userId, movieName, theatreName, seatNumbers, quantity);
    private final TicketDTO ticketDTO = new TicketDTO(ticketId, userId, movieName, theatreName, seatNumbers, quantity);

    @Test
    @DisplayName("AddTicket-Positive")
    void addTicket_positive() {
        when(mapper.map(ticketDTO)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(mapper.map(ticket)).thenReturn(ticketDTO);

        TicketDTO actual = ticketService.addTicket(ticketDTO);

        assertEquals(ticketDTO, actual);
    }

    @Test
    @DisplayName("GetAllTickets-Positive")
    void getAllTickets_positive() {
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));
        when(mapper.map(ticket)).thenReturn(ticketDTO);

        List<TicketDTO> actual = ticketService.getAllTickets();

        assertEquals(List.of(ticketDTO), actual);
    }

    @Test
    @DisplayName("GetAllTickets-Negative-TicketNotFound")
    void getAllTickets_negative_ticketNotFound() {
        when(ticketRepository.findAll()).thenReturn(List.of());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getAllTickets());
    }

    @Test
    @DisplayName("GetTicketByID-Positive")
    void getTicketByID_positive() {
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(mapper.map(ticket)).thenReturn(ticketDTO);

        TicketDTO actual = ticketService.getTicketByID(ticketId);

        assertEquals(ticketDTO, actual);
    }

    @Test
    @DisplayName("GetTicketByID-Negative-TicketNotFound")
    void getTicketByID_negative_ticketNotFound() {
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketByID(ticketId));
    }

    @Test
    @DisplayName("GetTicketsForMovieInTheatre-Positive")
    void getTicketsForMovieInTheatre_positive() {
        when(ticketRepository.findAllByMovieNameAndTheatreName(movieName, theatreName)).thenReturn(List.of(ticket));
        when(mapper.map(ticket)).thenReturn(ticketDTO);

        List<TicketDTO> actual = ticketService.getTicketsForMovieInTheatre(movieName, theatreName);

        assertEquals(List.of(ticketDTO), actual);
    }

    @Test
    @DisplayName("GetTicketsForUser-Positive")
    void getTicketsForUser_positive() {
        when(ticketRepository.findAllByUserID(userId)).thenReturn(List.of(ticket));
        when(mapper.map(ticket)).thenReturn(ticketDTO);

        List<TicketDTO> actual = ticketService.getTicketsForUser(userId);

        assertEquals(List.of(ticketDTO), actual);
    }

    @Test
    @DisplayName("GetTicketsForUser-Negative-TicketNotFound")
    void getTicketsForUser_negative_ticketNotFound() {
        when(ticketRepository.findAllByUserID(userId)).thenReturn(List.of());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketsForUser(userId));
    }

    @Test
    @DisplayName("GetBookedTickets-Positive")
    void getBookedTickets_positive() {
        Ticket anotherTicket = new Ticket(11L, userId, movieName, theatreName, "A3", 3);
        TicketDTO anotherDto = new TicketDTO(11L, userId, movieName, theatreName, "A3", 3);

        when(ticketRepository.findAllByMovieNameAndTheatreName(movieName, theatreName))
                .thenReturn(List.of(ticket, anotherTicket));
        when(mapper.map(ticket)).thenReturn(ticketDTO);
        when(mapper.map(anotherTicket)).thenReturn(anotherDto);

        long booked = ticketService.getBookedTickets(movieName, theatreName);

        assertEquals(5L, booked);
    }

    @Test
    @DisplayName("DeleteTicketsForMovieInTheatre-Positive")
    void deleteTicketsForMovieInTheatre_positive() {
        when(ticketRepository.findAllByMovieNameAndTheatreName(movieName, theatreName)).thenReturn(List.of(ticket));
        when(mapper.map(ticket)).thenReturn(ticketDTO);

        ticketService.deleteTicketsForMovieInTheatre(movieName, theatreName);

        verify(ticketRepository).deleteAllByIdInBatch(List.of(ticketId));
    }
}
