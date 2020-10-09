package com.example.informationappjava.ui.chat.chatlist.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.Utils.Utilities;
import com.example.informationappjava.ui.chat.chatlist.model.Chat;
import com.example.informationappjava.ui.chat.chatlist.model.ChatModel;
import com.example.informationappjava.xmpp.RoosterConnection;
import com.example.informationappjava.xmpp.RoosterConnectionService;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 *
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatHolder> {

  private static final String LOGTAG = "ChatListAdapter";

  /**
   *
   */
  public interface OnItemClickListener {
    void onItemClick(String contactJid, Chat.ContactType chatType);
  }

  /**
   *
   */
  public interface OnItemLongClickListener {
    void onItemLongClick(String contactJod, int chatUniqueId, View anchor);
  }

  List<Chat> chatList;
  private OnItemClickListener onItemClickListener;
  private OnItemLongClickListener onItemLongClick;
  private final Context mContext;

  /**
   * @param context
   */
  public ChatListAdapter(Context context) {
    this.chatList = ChatModel.get(context).getChats();
    this.mContext = context;
  }

  /**
   * @return
   */
  public OnItemClickListener getOnItemClickListener() {
    return onItemClickListener;
  }

  /**
   * @param onItemClickListener
   */
  public void setOnItemClickListener(
      OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  /**
   * @return
   */
  public OnItemLongClickListener getOnItemLongClick() {
    return onItemLongClick;
  }

  /**
   * @param onItemLongClick
   */
  public void setOnItemLongClick(
      OnItemLongClickListener onItemLongClick) {
    this.onItemLongClick = onItemLongClick;
  }

  /**
   * @param parent
   * @param viewType
   * @return
   */
  @NonNull
  @NotNull
  @Override
  public ChatHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.chat_list_item, parent, false);

    return new ChatHolder(view, this);
  }

  /**
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(@NonNull @NotNull ChatHolder holder, int position) {
    Chat chat = chatList.get(position);
    holder.bindChat(chat);
  }

  /**
   * @return
   */
  @Override
  public int getItemCount() {
    return chatList.size();
  }

  /**
   *
   */
  public void onChatCountChange() {
    chatList = ChatModel.get(mContext).getChats();
    notifyDataSetChanged();
    Log.d(LOGTAG, "ChatListAdapter knows of the change in message");
  }
}

/**
 *
 */
class ChatHolder extends RecyclerView.ViewHolder {

  private static final String LOGTAG = "ChatHolder";
  private final TextView contactTextView;
  private final TextView messageAbstractTextview;
  private final TextView timestampTextView;
  private final ImageView profileImage;
  private Chat mChat;
  private final ChatListAdapter chatListAdapter;

  /**
   * @param itemView
   * @param adapter
   */
  public ChatHolder(@NonNull @NotNull View itemView,
      ChatListAdapter adapter) {
    super(itemView);

    contactTextView = itemView.findViewById(R.id.contact_jid);
    messageAbstractTextview = itemView.findViewById(R.id.message_abstract);
    timestampTextView = itemView.findViewById(R.id.text_message_timestamp);
    profileImage = itemView.findViewById(R.id.profile);
    chatListAdapter = adapter;

    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        ChatListAdapter.OnItemClickListener listener = chatListAdapter.getOnItemClickListener();
        if (listener != null) {
          listener.onItemClick(contactTextView.getText().toString(), mChat.getContactType());

        }

        Log.d(LOGTAG, "Clicked on the item in the recyclerView");

      }
    });

    itemView.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        ChatListAdapter.OnItemLongClickListener listener = chatListAdapter.getOnItemLongClick();
        if (listener != null) {
          listener.onItemLongClick(mChat.getJid(), mChat.getPresistID(), itemView);
          return true;
        }
        return false;
      }
    });
  }

  /**
   * @param chat
   */
  public void bindChat(Chat chat) {

    mChat = chat;
    contactTextView.setText(chat.getJid());
    messageAbstractTextview.setText(chat.getLastMessage());
    timestampTextView.setText(Utilities.getFormattedTime(mChat.getLastMessageTimeStamp()));

    profileImage.setImageResource(R.drawable.ic_baseline_person_24);

    RoosterConnection rc = RoosterConnectionService.getConnection();
    if (rc != null) {
      String imageAbsPath = rc.getProfileImageAbsolutePath(mChat.getJid());
      if (imageAbsPath != null) {
        Drawable d = Drawable.createFromPath(imageAbsPath);
        profileImage.setImageDrawable(d);
      }
    }
  }
}