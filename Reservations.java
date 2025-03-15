import java.awt.image.ColorModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reservations {
    private final int CODE;
    private final String Room;
    private final Date CheckIn;
    private final Date CheckOut;
    private final double Rate;
    private final String LastName;
    private final String FirstName;
    private final int Adults;
    private final int Kids;

    public Reservations(int CODE, String Room, Date CheckIn, Date CheckOut, double Rate, String LastName, String FirstName, int Adults, int Kids) {
        this.CODE = CODE;
        this.Room = Room;
        this.CheckIn = CheckIn;
        this.CheckOut = CheckOut;
        this.Rate = Rate;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.Adults = Adults;
        this.Kids = Kids;
    }

    public String toString() {
        return String.format("Reservation %d | %s %s | Room: %s | Dates: %s to %s | Rate: %.2f | Guests: %d Adults, %d Kids",
                CODE, FirstName, LastName, Room, CheckIn, CheckOut, Rate, Adults, Kids);
    }

    public int getCode() { return CODE; }
    public String getRoom() { return Room; }
    public Date getCheckIn() { return CheckIn; }
    public Date getCheckOut() { return CheckOut; }
    public double getRate() { return Rate; }
    public String getLastName() { return LastName; }
    public String getFirstName() { return FirstName; }
    public int getAdults() { return Adults; }
    public int getKids() { return Kids; }

    // when the Reservations option is selected, the system shall accept user info including their first name, last
    // name, room code, bed type, begin date of stay, end of date stay, number of children, number of adults
    public static List<Reservations> getReservations(Connection conn) throws SQLException {
        List<Reservations> reservations = new ArrayList<>();
        String query = "SELECT * FROM lab7_reservations ORDER BY CheckIn";

        try (Statement stmt = conn.createStatement();
             ResultSet newRes = stmt.executeQuery(query)) {
            while (newRes.next()) {
                reservations.add(new Reservations(
                        newRes.getInt("CODE"),
                        newRes.getString("Room"),
                        newRes.getDate("CheckIn"),
                        newRes.getDate("Checkout"),
                        newRes.getDouble("Rate"),
                        newRes.getString("LastName"),
                        newRes.getString("FirstName"),
                        newRes.getInt("Adults"),
                        newRes.getInt("Kids")
                ));
            }
        }
        return reservations;
    }

    // when the Detailed Reservation Information option is selected, the system shall present a search prompt that
    // allows the user to enter any combination of the following fields: first name, last name, a range of dates,
    // room code, and reservation code
    public static Reservations getReservationByCode(Connection conn, int code) throws SQLException {
        String query = "SELECT * FROM lab7_reservations WHERE CODE = ?";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setInt(1, code);
            try (ResultSet newRes = pStmt.executeQuery()) {
                if (newRes.next()) {
                    return new Reservations(
                            newRes.getInt("CODE"),
                            newRes.getString("Room"),
                            newRes.getDate("CheckIn"),
                            newRes.getDate("Checkout"),
                            newRes.getDouble("Rate"),
                            newRes.getString("LastName"),
                            newRes.getString("FirstName"),
                            newRes.getInt("Adults"),
                            newRes.getInt("Kids")
                    );
                }
            }
        }
        return null;
    }

    // when the Reservation Cancellation option is selected, the system shall allow the user to cancel an
    // existing reservation
    public static boolean cancelReservation(Connection conn, int code) throws SQLException {
        String query = "DELETE FROM lab7_reservations WHERE CODE = ?";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) {
            pStmt.setInt(1, code);
            int rowsAffected = pStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
