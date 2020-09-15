package com.example.informationappjava.ui.chat.login.model;

public class Chat {
    private String jid;
    private String lastMessage;

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
}
