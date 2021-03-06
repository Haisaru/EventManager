package users;

import ScheduleSystem.Event;
import ScheduleSystem.EventManager;
import message_system.ConversationManager;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A use case class for user that manages user related activities.
 */
public class UserManager implements Serializable {
    private  ArrayList<User> allUsers;
    public User NotFoundUser = new User("NotFound", "NotFound");

    /**
     * Initializes this UserManager.
     */
    public UserManager() {
        this.allUsers = new ArrayList<>();
        //TODO: Initialize this.em
    }

    /**
     * Adds a user to this system.
     * @param newUser the user to be added.
     * @return true iff the user has been successfully added.
     */
    public boolean addUser(User newUser) {
        for (User u:allUsers){
            if (u.getUsername().equals(newUser.getUsername())){
                return false;
            }
        }

        allUsers.add(newUser);
        return true;


    }
    /**
     * Adds two users as friends.
     * @param user1 first user to be added.
     * @param user2 second user to be added.
     * @return true iff the two users have been befriended successfully.
     */
    public boolean addFriends(User user1, User user2) {
        if (!user1.isFriendWithID(user2.getID())){
            if(!user2.isFriendWithID(user1.getID())){
                user1.addFriend(user2.getID());
                user2.addFriend(user1.getID());
                return true;
            }
        }
        return false;
    }

    /**
     * Unfriends two users.
     * @param user1 first user to be unfriended.
     * @param user2 second user to be unfriended.
     * @return true iff the two users have been unfriended successfully.
     */
    public boolean deleteFriends(User user1, User user2) {
        if (user1.isFriendWithID(user2.getID())){
            if(user2.isFriendWithID(user1.getID())){
                user1.deleteFriend(user2.getID());
                user2.deleteFriend(user1.getID());
                return true;
            }
        }
        return false;
    }

    /**
     * Search the user with specific user name.
     * @param name the name in search.
     * @return the first user found with its username matching with <code>name</code>.
     */
    public User getUserByName(String name) {
        for (User u:allUsers) {
            if (u.getUsername().equals(name)) {
                return u;
            }
        }
        return NotFoundUser;
    }

    /**
     * Search the user with specific <code>UUID</code>
     * @param id the <code>UUID</code> in search.
     * @return the user that matches the <code>id</code>. If no use is found, return <code>NotFoundUser</code>.
     */
    public User getUserByID(UUID id){
        for (User u:allUsers){
            if (u.getID().equals(id)){
                return u;
            }
        }
        return NotFoundUser;
    }

    public ArrayList<User> getAllFriends(User user){
        ArrayList<User> allFriends= new ArrayList<>();
        for (UUID id:user.getFriends()){
            allFriends.add(getUserByID(id));
        }
        return allFriends;
    }

    /**
     * Returns all users in the system.
     * @return an ArrayList of all users in the system.
     */
    public ArrayList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Returns all speakers in the system.
     * @return an ArrayList of all speakers in the system.
     */
    public ArrayList<Speaker> getAllSpeakers(){
        ArrayList<Speaker> speakers = new ArrayList<>();
        for (User u: allUsers){
            if (u.getType().equals("s")){
                speakers.add((Speaker)u);
            }
        }
        return speakers;
    }

    /**
     * Returns all speakers in the system.
     * @@return an ArrayList of all speakers in the system.
     */
    public ArrayList<User> getAllSpeakersUser(){
        ArrayList<User> speakers = new ArrayList<>();
        for (User u: allUsers){
            if (u.getType().equals("s")){
                speakers.add(u);
            }
        }
        return speakers;
    }

    /**
     * Returns all the names of the speakers in the system.
     * @return an ArrayList of all the names of speakers in the system.
     */
    public ArrayList<String> getAllSpeakerNames(){
        ArrayList<String> speakers = new ArrayList<>();
        for (User u: allUsers){
            if (u.getType().equals("s")){
                speakers.add(u.getUsername());
            }
        }
        return speakers;
    }

    /**
     * Enroll an user for an ever, add the id of the event to the user's event ID list.
     * @param event event to be added.
     * @param user user for the event to be added.
     */
    public void addEventForUser(Event event, User user) {
        user.addEvent(event.getId());
    }

    /**
     * Remove an user for an ever, remove the id of the event from the user's event ID list.
     * @param event event to be removed.
     * @param user user for the event to be removed.
     */
    public void removeEvent(Event event, User user) {
        user.removeEvent(event.getId());
    }


    /**
     * Returns all the attendees in the system.
     * @return an ArrayList of all attendees in the system.
     */
    public ArrayList<User> getAllAttendees() {
        ArrayList<User> attendees = new ArrayList<>();
        for (User u:allUsers){
            if (u.getType().equals("a")){
                attendees.add(u);
            }
        }
        return attendees;

    }

    /**
     * Add a speaker to the system.
     * @param username username of the speaker
     * @param password password of the speaker
     * @return true iff the speaker has been successfully added.
     */
    public boolean createSpeaker(String username, String password){
        return addUser(new Speaker(username, password));
    }
}