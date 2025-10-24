package com.coworkly.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SlotAlreadyBookedException extends RuntimeException {
    public SlotAlreadyBookedException(Long timeSlotId) {
        super("Timeslot already booked: " + timeSlotId);
    }
}
