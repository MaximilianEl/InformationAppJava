package com.example.informationappjava.ui.chat.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {

    private static ChatModel sChatsModel;
    private Context mContext;

    public static ChatModel get(Context context)
    {
        if(sChatsModel == null)
        {
            sChatsModel = new ChatModel(context);
        }
        return sChatsModel;
    }

    private ChatModel(Context context)
    {
        mContext = context;
    }

    public List<Chat> getChats()
    {
        List<Chat> chats = new ArrayList<>();
        Chat chat1 = new Chat("user1@server1.com", "Hey there");
        chats.add(chat1);
        Chat chat2 = new Chat("user2@server2.com", "Hey there");
        chats.add(chat2);
        Chat chat3 = new Chat("user3@server3.com", "Hey there");
        chats.add(chat3);
        Chat chat4 = new Chat("user4@server4.com", "Hey there");
        chats.add(chat4);
        Chat chat5 = new Chat("user5@server5.com", "Hey there");
        chats.add(chat5);
        Chat chat6 = new Chat("user6@server6.com", "Hey there");
        chats.add(chat6);
        Chat chat7 = new Chat("user7@server7.com", "OH FUCK YEAH GENAU DA");
        chats.add(chat7);
        chats.add(chat7);
        chats.add(chat7);
        chats.add(chat7);
        chats.add(chat7);
        chats.add(chat7);
        chats.add(chat7);
        chats.add(chat7);


        return chats;
    }

}
