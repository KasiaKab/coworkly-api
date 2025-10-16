package com.coworkly.app.exception;

public class SlotAlreadyBookedException extends RuntimeException {
    public SlotAlreadyBookedException(Long timeSlotId) {
        super("Timeslot already booked: " + timeSlotId);
    }
}
