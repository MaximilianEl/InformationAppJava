package com.example.informationappjava.ui.chat.view.model;

import android.content.Context;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatMessagesModel {

  private static ChatMessagesModel chatMessagesModel;
  private Context context;
  List<ChatMessage> messages;

  public static ChatMessagesModel get(Context context) {
    if (chatMessagesModel == null){
      chatMessagesModel = new ChatMessagesModel(context);
    }

    return chatMessagesModel;
  }

  private ChatMessagesModel(Context context) {
    this.context = context;

    messages = new ArrayList<>();

  }

  public List<ChatMessage> getMessages() {
    return messages;
  }

  public void addMessage(ChatMessage message) {
    messages.add(message);
  }

}
