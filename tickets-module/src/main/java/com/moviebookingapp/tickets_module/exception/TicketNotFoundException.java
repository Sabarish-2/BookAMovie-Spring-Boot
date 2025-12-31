package com.moviebookingapp.tickets_module.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class TicketNotFoundException extends CustomException {

    @Serial
    private static final long serialVersionUID = 4L;

    public TicketNotFoundException() {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No Tickets Found!");
    }
    public TicketNotFoundException(String movieName, String theatreName) {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No Tickets Found with Movie: " + movieName + " at " + theatreName);
    }
    public TicketNotFoundException(String movieName, String theatreName, String userID) {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No Tickets Found with Movie: " + movieName + " at " + theatreName + " for " + userID);
    }
    public TicketNotFoundException(Long ticketID) {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No Tickets Found with ID: " + ticketID);
    }
    public TicketNotFoundException(String userID) {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No Tickets Found for User ID: " + userID);
    }
}