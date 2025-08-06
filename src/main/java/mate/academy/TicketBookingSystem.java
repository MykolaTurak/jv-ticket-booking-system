package mate.academy;

import java.util.concurrent.Semaphore;

public class TicketBookingSystem {
    private final Semaphore semaphore;
    private int totalSeats;

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
        semaphore = new Semaphore(1);
    }

    public BookingResult attemptBooking(String user) {
        boolean success = false;
        String message = "No seats available.";

        try {
            semaphore.acquire();
            try {
                if (totalSeats > 0) {
                    totalSeats--;
                    success = true;
                    message = "Booking successful.";
                }
            } finally {
                semaphore.release();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new BookingResult(user, success, message);
    }

}
