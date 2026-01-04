package com.moviebookingapp.movie_and_theatre_module.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tickets-module")
public interface TicketsClient {
    @GetMapping("/tickets/booked/{movieName}/{theatreName}")
    ResponseEntity<Long> getBookedTickets(@PathVariable String movieName, @PathVariable String theatreName);

    @DeleteMapping("/tickets/{movieName}/delete/{theatreName}")
    ResponseEntity<String> deleteTicketsForTicketInTheatre(@PathVariable String movieName, @PathVariable String theatreName);
}
