package entities;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The event entity is a class responsible for the events in the convention. Each instance of an event models a
 * separate event.
 */
public abstract class Event implements Serializable {
    protected UUID id;
    protected String name;
    protected ZonedDateTime startTime;
    protected ZonedDateTime endTime;
    protected int capacity;
    protected ArrayList<UUID> attendees;
    protected UUID room;
    protected String description = "No information on this event has been disclosed yet.";
    protected ZoneId defaultZone = ZoneId.of("UTC-5");
    /**
     * @param name the name of the event
     * @param capacity the maximum attendee capacity of the event
     */
    public Event(String name, int capacity,ZonedDateTime startTime, ZonedDateTime endTime){
        this.name = name.toUpperCase();
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.attendees = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    /**
     * Second constructor for event without for case there is no event information.
     */
    public Event() {
        this.id = UUID.randomUUID();
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAttendees(ArrayList<UUID> attendees) {
        this.attendees = attendees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the id of the event
     */
    public UUID getId(){
        return this.id;
    }

    /**
     * @return the name of the event
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return date of the event as a date object
     */


    /**
     * @return the the maximum capacity of the event
     */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * @set the capacity
     */
    public void setCapacity(int c){this.capacity = c; }

    /**
     * @return the current amount of attendees registered for the event
     */
    public int currentNum(){
        return attendees.size();
    }

    /**
     * @return an ArrayList of the attendees
     */
    public ArrayList<UUID> getAttendees() {
        return attendees;
    }



    /**
     * @return the assigned room of the event if it exists. Null otherwise.
     */
    public UUID getRoom(){
        if (this.assignedRoom()){
            return room;
        }
        return null;
    }

    /**
     * add an individual that will attend the event
     * @param userID the user id of the attendee
     * @return true if and only if the attendee was successfully added (the attendee was not already registered)
     */
    public boolean addAttendee(UUID userID){
        if (attendees.contains(userID)){
            return false;
        }
        attendees.add(userID);
        return true;
    }

    /**
     * check the event if an attendee with a specified user id is expected to attend the event
     * @param userID the user id of the attendee
     * @return true if and only if the attendee with the specified user id is attending the event
     */
    public boolean hasAttendee(UUID userID){
        return attendees.contains(userID);
    }

    /**
     * attempt to remove an attendee with a specified user id from the event
     * @param userID the user id of the specified attendee
     * @return true if and only if the user with the specified user id is removed from the event
     */
    public boolean removeAttendee(UUID userID){
        if (attendees.contains(userID)){
            attendees.remove(userID);
            return true;
        }
        return false;
    }

    /**
     * @return true if and only if the event is not at max capacity
     */
    public boolean hasSpace(){
        return this.currentNum() < this.capacity;
    }

    /**
     * @return true if and only if the event has an assigned room
     */
    public boolean assignedRoom(){
        return room != null;
    }

    /**
     * set the room of the event to the specified room
     * @param room the room to set the event location to
     */
    public void setRoom(UUID room) {
        this.room = room;
    }

    /**
     * remove the room assigned to the event if it exists so that the event has no specified room
     */
    public void removeRoom(){
        room = null;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String full;
        if (currentNum() < capacity) {
            full = "available";
        } else {
            full = "full";
        }

        return name+"@"+ startTime + ", status: "+ attendees.size() + "/" + capacity + " " + full;
    }

    public String getStatus(){
        ZonedDateTime.now(defaultZone);
        if ((ZonedDateTime.now(defaultZone).compareTo(startTime)) >= 0) {
            return "past";
        }
        if (hasSpace()){
            return "available";
        }
        return "full";
    }
}
