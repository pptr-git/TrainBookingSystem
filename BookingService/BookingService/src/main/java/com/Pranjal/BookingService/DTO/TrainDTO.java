package com.Pranjal.BookingService.DTO;


public class TrainDTO {
    private Long id;
    private int availableSeats;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}
