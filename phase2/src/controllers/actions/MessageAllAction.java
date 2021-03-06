package controllers.actions;

import entities.Conversation;
import entities.Message;
import entities.User;
import use_cases.ConversationManager;
import use_cases.UserManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

public abstract class MessageAllAction extends MessageAction {

    public MessageAllAction(UUID userID, UserManager um, ConversationManager cm) {super(userID, um, cm);}

    private UUID conversationID;

    public abstract void run() throws Exception;
    public abstract String getName();

    /**
     * Helper method for messaging all
     * @param users ArrayList of users to message
     * @param bdy Body of the message
     * @param title Title of the message
     */
    public void handleMessage(ArrayList<User> users, String bdy, String title) {
        UUID conID = this.cm.newConversation();
        cm.addUserToConversation(conID, userID);
        cm.setUserOwner(conID, userID);

        for (User value : users) {
            cm.addUserToConversation(conID, value.getID());
        }

        cm.setConversationName(conID, title);
        cm.sendMessageInConversation(conID, userID, bdy);
        cm.setConversationReadOnly(conID, true);
        this.conversationID = conID;
    }

    public UUID getConversationID() {
        return this.conversationID;
    }

    /*
    public void handleMessageAll(ArrayList<User> users) {
        if (users.isEmpty()) {
            System.out.println("No Users to message");
            return;
        }


        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter your Message");
            String inp = br.readLine();

            UUID conID = this.cm.newConversation();
            cm.addUserToConversation(conID, userID);
            cm.setUserOwner(conID, userID);

            for (User value : users) {
                cm.addUserToConversation(conID, value.getID());
            }

            System.out.println("Enter your message title");
            String inpName = br.readLine();

            cm.setConversationName(conID, inpName);
            cm.sendMessageInConversation(conID, userID, inp);
            cm.setConversationReadOnly(conID, true);
        } catch (IOException e) {
            System.out.println("Failed to read input.");
        }
    }
    */
}
