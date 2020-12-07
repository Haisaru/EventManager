package database;

import entities.Conversation;
import entities.Message;
import gateways.MessageDataGateway;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MessageDataMapper implements MessageDataGateway  {
    private Database db;

    public MessageDataMapper() {
        db = new Database();
    }

    @Override
    public void insertMessage(Message msg) {
        try {
            db.insertNewMessage(msg.getMessageID(), msg.getBody(), msg.getSenderID(), (Date) msg.getTimeSent());
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to insert a new message.");
            e.printStackTrace();
        }
    }

    @Override
    public void insertConversation(Conversation c) {
        try {
            db.insertNewConversation(c.getID(), c.getMembers(), c.getName(), c.getReadOnly() ? 1 : 0, c.getOwner());
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to insert a new conversation.");
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Message> fetchMessages() {
        try {
            ResultSet rs = db.getAllMessages();
            ArrayList<Message> out = new ArrayList<>();
            while(rs.next()) {
                Message msg = new Message(UUID.fromString(rs.getString("body")), rs.getString("senderID"));
                //TODO: Message ID and Date needs a setter.
                out.add(msg);
            }
            return out;
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to get all messages.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Message> fetchAllUserMessages(UUID userID) {
        try {
            ResultSet rs = db.getAllUserMessages(userID);
            ArrayList<Message> out = new ArrayList<>();
            while(rs.next()) {
                Message msg = new Message(UUID.fromString(rs.getString("body")), rs.getString("senderID"));
                //TODO: Message ID and Date needs a setter.
                out.add(msg);
            }
            return out;
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to get all messages.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public ArrayList<Conversation> fetchConversations() {
        try {
            ResultSet rs = db.getAllConversations();
            ArrayList<Conversation> out = new ArrayList<>();
            while (rs.next()) {
                // Data retrieval
                Conversation c = new Conversation();
                //TODO: Conversation ID needs a setter.
                UUID conID = UUID.fromString(rs.getString("uuid"));
                String rawMembers = (String) rs.getObject("members");
                //TODO: Not sure what this looks like in DB yet so needs to be parsed still
                String rawUnreadMsgs = (String) rs.getObject("unreadMessages");
                String convName = rs.getString("convName");
                String strOwnerID = rs.getString("owner");


                // Building the conversation
                //TODO: Make this a protected parse method
                if (rawMembers != null) {
                    rawMembers = rawMembers.substring(1, rawMembers.length() - 1); // Remove the "[" and "]" from string
                    String[] membersList = rawMembers.split(", ");
                    for (String s: membersList) {
                        c.addMember(UUID.fromString(s));
                    }
                }
                c.setName(convName);
                if (strOwnerID != null) {
                    c.setOwner(UUID.fromString(strOwnerID));
                }

                out.add(c);
            }

            return out;
        } catch (SQLException e) {
            System.out.println("Something went wrong trying to get all conversations.");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void updateConversationOwner(Conversation c) {
        try {
            db.updateConversationOwner(c.getID(), c.getOwner());
        } catch(SQLException e) {
            System.out.println("Something went wrong try to update that conversation.");
            e.printStackTrace();
        }
    }

    public void updateConversationName(Conversation c) {
        try {
            db.updateConversationName(c.getID(), c.getName());
        } catch(SQLException e) {
            System.out.println("Something went wrong try to update that conversation.");
            e.printStackTrace();
        }
    }

    public void updateConversationMembers(Conversation c) {
        try {
            db.updateConversationMembers(c.getID(), c.getMembers());
        } catch(SQLException e) {
            System.out.println("Something went wrong try to update that conversation.");
            e.printStackTrace();
        }
    }

    public void updateConversationReadOnly(Conversation c) {
        try {
            db.updateConversationReadOnly(c.getID(), c.getReadOnly());
        } catch(SQLException e) {
            System.out.println("Something went wrong try to update that conversation.");
            e.printStackTrace();
        }
    }
}