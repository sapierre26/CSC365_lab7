import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Rooms {
    private final String RoomCode;
    private final String RoomName;
    private final int Beds;
    private final String bedType;
    private final int maxOcc;
    private final double basePrice;
    private final String decor;

    public Rooms(String RoomCode, String RoomName, int Beds, String bedType, int maxOcc, double basePrice, String decor) {
        this.RoomCode = RoomCode;
        this.RoomName = RoomName;
        this.Beds = Beds;
        this.bedType = bedType;
        this.maxOcc = maxOcc;
        this.basePrice = basePrice;
        this.decor = decor;
    }

    public String toString() {
        return String.format("Room %s: %s | Beds: %d (%s) | Max Occ: %d | Price: $%.2f | Decor: %s",
                RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor);
    }

    public String getRoomCode() { return RoomCode; }
    public String getRoomName() { return RoomName; }
    public int getBeds() { return Beds; }
    public String getBedType() { return bedType; }
    public int getMaxOcc() { return maxOcc; }
    public double getBasePrice() { return basePrice; }
    public String getDecor() { return decor; }

    // when the Rooms and Rates option is selected, the system shall output a list of rooms to the user
    // the output should be sorted by popularity (high to low)
    // include the room popularity score, next available check-in date, length in days of the room stay, and the check-out date
    public static List<Rooms> getRooms(Connection conn) throws SQLException {
        List<Rooms> rooms = new ArrayList<>();
        String query = "SELECT * FROM lab7_rooms ORDER BY RoomName";

        try (Statement stmt = conn.createStatement();
             ResultSet newRoom = stmt.executeQuery(query)) { // this should allow us to execute SQL queries
            while (newRoom.next()) {
                rooms.add(new Rooms( // meant to create a new Rooms object
                        newRoom.getString("RoomCode"),
                        newRoom.getString("RoomName"),
                        newRoom.getInt("Beds"),
                        newRoom.getString("bedType"),
                        newRoom.getInt("maxOcc"),
                        newRoom.getDouble("basePrice"),
                        newRoom.getString("decor")
                ));
            }
        }
        return rooms;
    }

    public static Rooms getRoomByCode(Connection conn, String roomCode) throws SQLException {
        String query = "SELECT * FROM lab7_rooms WHERE RoomCode = ?";

        try (PreparedStatement pStmt = conn.prepareStatement(query)) { // this should allow us to execute SQL queries
            pStmt.setString(1, roomCode);
            try (ResultSet newRoom = pStmt.executeQuery()) { // this should allow us to execute SQL queries
                if (newRoom.next()) {
                    return new Rooms(
                            newRoom.getString("RoomCode"),
                            newRoom.getString("RoomName"),
                            newRoom.getInt("Beds"),
                            newRoom.getString("bedType"),
                            newRoom.getInt("maxOcc"),
                            newRoom.getDouble("basePrice"),
                            newRoom.getString("decor")
                    );
                }
            }
        }
        return null; // if there is no room found
    }
}
