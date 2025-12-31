package com.moviebookingapp.tickets_module.repositories;

import com.moviebookingapp.tickets_module.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByMovieNameAndTheatreName(String movieName, String theatreName);

    List<Ticket> findAllByUserID(String userID);
}