package database;

import entities.Room;
import gateways.RoomDataGateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class RoomDataMapper implements RoomDataGateway {
    private Database db = new Database();

    @Override
    public ArrayList<Room> fetchRooms() {
        try {
            ResultSet rs = db.getAllRooms();
            ArrayList<Room> out = new ArrayList<>();

            while (rs.next()) {
                Room room = new Room(rs.getInt("capacity"), rs.getString("name"));
                room.setID(UUID.fromString(rs.getString("uuid")));

                String rawEvents = (String) rs.getObject("events");

                if (rawEvents != null && !rawEvents.equals("[]")) {
                    rawEvents = rawEvents.substring(1, rawEvents.length() - 1); // Remove the "[" and "]" from string
                    String[] membersList = rawEvents.split(", ");
                    for (String s: membersList) {
                        room.addEvent(UUID.fromString(s));
                    }
                }

                out.add(room);

            }

            return out;
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to get all rooms.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void insertRoom(Room room) {
        try {
            db.insertRoom(room.getID(), room.getRoomName(), room.getCapacity());
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to insert that room.");
            e.printStackTrace();
        }
    }

    @Override
    public void updateRoomEvents(Room room) {
        try {
            db.updateRoomEvents(room.getID(), room.getEvents());
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to update that room.");
            e.printStackTrace();
        }
    }
}
