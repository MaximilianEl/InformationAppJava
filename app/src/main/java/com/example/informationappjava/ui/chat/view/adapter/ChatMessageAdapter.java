package com.example.informationappjava.ui.chat.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import com.example.informationappjava.ui.chat.view.model.ChatMessagesModel;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageViewHolder> {

  private static final int SENT = 1;
  private static final int RECEIVED = 2;
  private static final String LOGTAG = "ChatMessageAdapter";

  private List<ChatMessage> chatMessageList;
  private LayoutInflater layoutInflater;
  private Context context;
  private String contactJid;

  public ChatMessageAdapter(Context context, String contactJid){
    this.layoutInflater = LayoutInflater.from(context);
    this.context = context;
    this.contactJid = contactJid;

    chatMessageList = ChatMessagesModel.get(context).getMessages();
  }

  @Override
  public ChatMessageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
      int viewType) {
    View itemView;
    switch (viewType) {
      case SENT :
        itemView = layoutInflater.inflate(R.layout.chat_message_sent, parent, false);
        return new ChatMessageViewHolder(itemView);

      case RECEIVED:
        itemView = layoutInflater.inflate(R.layout.chat_message_received, parent, false);
        return new ChatMessageViewHolder(itemView);

      default:
        itemView = layoutInflater.inflate(R.layout.chat_message_sent, parent, false);
        return new ChatMessageViewHolder(itemView);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull ChatMessageViewHolder holder, int position) {
    ChatMessage chatMessage = chatMessageList.get(position);
    holder.bindChat(chatMessage);
  }

  @Override
  public int getItemCount() {
    return chatMessageList.size();
  }

  @Override
  public int getItemViewType(int position) {
    ChatMessage.Type messageType = chatMessageList.get(position).getType();
    if (messageType == Type.SENT){
      return SENT;
    } else {
      return RECEIVED;
    }
  }
}

class ChatMessageViewHolder extends RecyclerView.ViewHolder {

  private TextView messageBody;
  private TextView messageTimestamp;
  private ImageView profileImage;

  public ChatMessageViewHolder(View itemView) {
    super(itemView);

    messageBody = itemView.findViewById(R.id.message_Body);
    messageTimestamp = itemView.findViewById(R.id.chat_textMessageTimestamp);
    profileImage = itemView.findViewById(R.id.profile);
  }

  public void bindChat(ChatMessage chatMessage){
    messageBody.setText(chatMessage.getMessage());
    messageTimestamp.setText(chatMessage.getFormattedTime());
    profileImage.setImageResource(R.drawable.ic_baseline_person_24);
  }
}
