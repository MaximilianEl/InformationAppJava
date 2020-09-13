package com.example.informationappjava.ui.chat.view.model;

import android.content.Context;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatMessagesModel {

  public interface OnMessageAddListener {
    void onMessageAdd();
  }

  private static ChatMessagesModel chatMessagesModel;
  private Context context;
  List<ChatMessage> messages;
  OnMessageAddListener messageAddListener;

  public void setMessageAddListener(
      OnMessageAddListener messageAddListener) {
    this.messageAddListener = messageAddListener;
  }

  public static ChatMessagesModel get(Context context) {
    if (chatMessagesModel == null){
      chatMessagesModel = new ChatMessagesModel(context);
    }

    return chatMessagesModel;
  }

  private ChatMessagesModel(Context context) {
    this.context = context;

    ChatMessage chatMessage1 = new ChatMessage("Wazzup?", System.currentTimeMillis(), Type.SENT, "user@example.com");

    ChatMessage chatMessage2 = new ChatMessage("Wuuzzup?" , System.currentTimeMillis(), Type.RECEIVED, "user2@example.com");

    messages = new ArrayList<>();
    messages.add(chatMessage1);
    messages.add(chatMessage2);
    messages.add(chatMessage1);
    messages.add(chatMessage2);
    messages.add(chatMessage1);
    messages.add(chatMessage2);

  }

  public List<ChatMessage> getMessages() {
    return messages;
  }

  public void addMessage(ChatMessage message) {
    messages.add(message);
    messageAddListener.onMessageAdd();
  }

}
