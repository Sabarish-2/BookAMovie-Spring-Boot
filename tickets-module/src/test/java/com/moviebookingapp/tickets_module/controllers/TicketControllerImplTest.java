package com.moviebookingapp.tickets_module.controllers;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;
import com.moviebookingapp.tickets_module.exception.TicketNotFoundException;
import com.moviebookingapp.tickets_module.services.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketControllerImplTest {

    @InjectMocks
    private TicketControllerImpl ticketController;

    @Mock
    private TicketService ticketService;

    private final String movieName = "World War -I";
    private final String theatreName = "PSR";
    private final String userId = "user-123";
    private final Long ticketId = 10L;
    private final TicketDTO ticketDTO = new TicketDTO(ticketId, userId, movieName, theatreName, "A1,A2", 2);

    @Test
    @DisplayName("CreateTicket-Positive")
    void createTicket_positive() {
        when(ticketService.addTicket(ticketDTO)).thenReturn(ticketDTO);

        ResponseEntity<TicketDTO> response = ticketController.createTicket(ticketDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ticketDTO, response.getBody());
    }

    @Test
    @DisplayName("ViewAllTickets-Positive")
    void viewAllTickets_positive() {
        when(ticketService.getAllTickets()).thenReturn(List.of(ticketDTO));

        ResponseEntity<List<TicketDTO>> response = ticketController.viewAllTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(ticketDTO), response.getBody());
    }

    @Test
    @DisplayName("ViewAllTickets-Negative-TicketNotFound")
    void viewAllTickets_negative_ticketNotFound() {
        when(ticketService.getAllTickets()).thenThrow(new TicketNotFoundException());

        assertThrows(TicketNotFoundException.class, () -> ticketController.viewAllTickets());
    }

    @Test
    @DisplayName("GetTicketByID-Positive")
    void getTicketByID_positive() {
        when(ticketService.getTicketByID(ticketId)).thenReturn(ticketDTO);

        ResponseEntity<TicketDTO> response = ticketController.getTicketByID(ticketId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticketDTO, response.getBody());
    }

    @Test
    @DisplayName("GetTicketByID-Negative-TicketNotFound")
    void getTicketByID_negative_ticketNotFound() {
        when(ticketService.getTicketByID(ticketId)).thenThrow(new TicketNotFoundException(ticketId));

        assertThrows(TicketNotFoundException.class, () -> ticketController.getTicketByID(ticketId));
    }

    @Test
    @DisplayName("GetTicketsForMovieInTheatre-Positive")
    void getTicketsForMovieInTheatre_positive() {
        when(ticketService.getTicketsForMovieInTheatre(movieName, theatreName)).thenReturn(List.of(ticketDTO));

        ResponseEntity<List<TicketDTO>> response = ticketController.getTicketsForMovieInTheatre(movieName, theatreName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(ticketDTO), response.getBody());
    }

    @Test
    @DisplayName("GetTicketsForUser-Positive")
    void getTicketsForUser_positive() {
        when(ticketService.getTicketsForUser(userId)).thenReturn(List.of(ticketDTO));

        ResponseEntity<List<TicketDTO>> response = ticketController.getTicketsForUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(ticketDTO), response.getBody());
    }

    @Test
    @DisplayName("GetTicketsForUser-Negative-TicketNotFound")
    void getTicketsForUser_negative_ticketNotFound() {
        when(ticketService.getTicketsForUser(userId)).thenThrow(new TicketNotFoundException(userId));

        assertThrows(TicketNotFoundException.class, () -> ticketController.getTicketsForUser(userId));
    }

    @Test
    @DisplayName("GetBookedTickets-Positive")
    void getBookedTickets_positive() {
        when(ticketService.getBookedTickets(movieName, theatreName)).thenReturn(3L);

        ResponseEntity<Long> response = ticketController.getBookedTickets(movieName, theatreName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3L, response.getBody());
    }

    @Test
    @DisplayName("DeleteTicketsForMovieInTheatre-Positive")
    void deleteTicketsForMovieInTheatre_positive() {
        ResponseEntity<String> response = ticketController.deleteTicketsForMovieInTheatre(movieName, theatreName);

        verify(ticketService).deleteTicketsForMovieInTheatre(movieName, theatreName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tickets for Movie: " + movieName + " In " + theatreName + " were Deleted Successfully!",
                response.getBody());
    }

    @Test
    @DisplayName("DeleteTicketsForMovieInTheatre-Negative-TicketNotFound")
    void deleteTicketsForMovieInTheatre_negative_ticketNotFound() {
        doThrow(new TicketNotFoundException(movieName)).when(ticketService)
                .deleteTicketsForMovieInTheatre(movieName, theatreName);

        assertThrows(TicketNotFoundException.class,
                () -> ticketController.deleteTicketsForMovieInTheatre(movieName, theatreName));
    }
}
