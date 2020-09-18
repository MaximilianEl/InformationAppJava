package com.example.informationappjava.ui.chat.login.model;

public class Chat {
    private String jid;
    private String lastMessage;
    private long lastMEssageTimeStamp;
    private ContactType contactType;
    private int presistID;
    private long unreadCount;

    public static final String TABLE_NAME = "chats";

    public static final class Cols {
        public static final String CHAT_UNIQUE_ID = "contactUniqueId";
        public static final String CONTACT_JID = "jid";
        public static final String CONTACT_TYPE = "contactType";
        public static final String LAST_MESSAGE = "lastMessage";
        public static final String UNREAD_COUNT = "unreadCount";
        public static final String LAST_MESSAGE_TIME_STAMP = "lastMessageTimeStamp";
    }

    public Chat(String jid, String lastMessage) {
        this.jid = jid;
        this.lastMessage = lastMessage;
    }

    public String getJid() {
        return jid;
    }

    public String getLastMessage() {
        return lastMessage;
    }



    public String getTypeStringValue(ContactType type) {
        if (type == ContactType.ONE_ON_ONE)
            return "ONE_ON_ONE";
        else
            return "GROUP";
    }

    public enum ContactType {
        ONE_ON_ONE, GROUP
    }
}
