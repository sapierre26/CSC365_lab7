import java.awt.image.ColorModel;
import java.sql.*;

public class Reservations {
    private final int CODE;
    private final String Room;
    private final Date CheckIn;
    private final Date CheckOut;
    private final String LastName;
    private final String FirstName;
    private final int Adults;
    private final int Kids;

    public Reservations(int CODE, String Room, Date CheckIn, Date CheckOut, String LastName, String FirstName, int Adults, int Kids) {
        this.CODE = CODE;
        this.Room = Room;
        this.CheckIn = CheckIn;
        this.CheckOut = CheckOut;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.Adults = Adults;
        this.Kids = Kids;
    }

    public String toString() {
        return String.format("Reservation %d | %s %s | Room: %s | Dates: %s to %s | Guests: %d Adults, %d Kids",
                CODE, FirstName, LastName, Room, CheckIn, CheckOut, Adults, Kids);
    }

    public int getCode() { return CODE; }
    public String getRoom() { return Room; }
    public Date getCheckIn() { return CheckIn; }
    public Date getCheckOut() { return CheckOut; }
    public String getLastName() { return LastName; }
    public String getFirstName() { return FirstName; }
    public int getAdults() { return Adults; }
    public int getKids() { return Kids; }
}
