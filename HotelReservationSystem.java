import java.sql.*;
import java.util.*;
import java.sql.Date;

public class HotelReservationSystem {
    private final Connection conn;

    public HotelReservationSystem(Connection conn) {
        this.conn = conn;
    }

    public void viewRooms() {
        try {
            List<Rooms> rooms = Rooms.getRooms(conn);
            if (rooms.isEmpty()) {
                System.out.println("There are no rooms available");
            } else {
                for (Rooms room : rooms) {
                    System.out.println(room);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void viewReservations() {
        try {
            List<Reservations> reservations = Reservations.getReservations(conn);
            if (reservations.isEmpty()) {
                System.out.println("There is no reservation");
            } else {
                for (Reservations reservation : reservations) {
                    System.out.println(reservation);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void makeReservation(String RoomCode, String FirstName, String LastName, Date CheckIn, Date CheckOut, double Rate, int Adults, int Kids) {
        try {
            Rooms room = Rooms.getRoomByCode(conn, RoomCode);
            if (room != null) {
                // checks if the room is available for the given dates (This can be expanded to check actual availability)
                System.out.println("Room available: " + room);
                String insertReservationSQL = "INSERT INTO lab7_reservations (Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pStmt = conn.prepareStatement(insertReservationSQL, Statement.RETURN_GENERATED_KEYS)) {
                    pStmt.setString(1, RoomCode);
                    pStmt.setDate(2, CheckIn);
                    pStmt.setDate(3, CheckOut);
                    pStmt.setDouble(4, Rate);
                    pStmt.setString(5, LastName);
                    pStmt.setString(6, FirstName);
                    pStmt.setInt(7, Adults);
                    pStmt.setInt(8, Kids);

                    int rowsAffected = pStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                System.out.println("Reservation Confirmation Code: " + generatedKeys.getInt(1));
                            }
                        }
                    }
                }
            } else {
                System.out.println("There's no room");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void cancelReservation(int reservationCode) {
        try {
            boolean success = Reservations.cancelReservation(conn, reservationCode);
            if (success) {
                System.out.println("Cancellation successfully");
            } else {
                System.out.println("There are no reservations that match the given code");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://db.labthreesixfive.com/amustang?autoReconnect=true&useSSL=false";
            String user = "username";
            String password = "password";
            Connection conn = DriverManager.getConnection(url, user, password);

            HotelReservationSystem system = new HotelReservationSystem(conn);

            system.viewRooms();
            system.viewReservations();

            conn.close();  // Close the connection
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
