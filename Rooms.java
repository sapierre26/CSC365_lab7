import java.sql.*;

public class Rooms {
    private final String RoomCode;
    private final String RoomName;
    private final int Beds;
    private final String bedType;
    private final int maxOcc;
    private final float basePrice;
    private final String decor;

    public Rooms(String RoomCode, String RoomName, int Beds, String bedType, int maxOcc, float basePrice, String decor) {
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
    public float getBasePrice() { return basePrice; }
    public String getDecor() { return decor; }
}
