package com.moviebookingapp.tickets_module.mappers;

import com.moviebookingapp.tickets_module.dtos.TicketDTO;
import com.moviebookingapp.tickets_module.entities.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketDTO map(Ticket movie);

    Ticket map(TicketDTO movieDto);
}