package com.example.informationappjava.ui.chat.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.Utils.Utilities;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import com.example.informationappjava.ui.chat.view.model.ChatMessagesModel;

import java.util.List;

import com.example.informationappjava.xmpp.ChatConnection;
import com.example.informationappjava.xmpp.ChatConnectionService;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageViewHolder> {

  /**
   *
   */
  public interface OnInformRecyclerViewToScrollDownListener {

    void onInformRecyclerViewToScrollDown(int size);
  }

  /**
   *
   */
  public interface OnItemLongClickListener {

    void onItemLongClick(int uniqueId, View anchor);
  }

  private static final int SENT = 1;
  private static final int RECEIVED = 2;
  private static final String LOGTAG = "ChatMessageAdapter";

  private List<ChatMessage> chatMessageList;
  private final LayoutInflater layoutInflater;
  private Context context;
  private final String contactJid;
  private OnInformRecyclerViewToScrollDownListener onInformRecyclerViewToScrollDownListener;
  private OnItemLongClickListener onItemLongClickListener;

  /**
   * @param onInformRecyclerViewToScrollDownListener
   */
  public void setOnInformRecyclerViewToScrollDownListener(
      OnInformRecyclerViewToScrollDownListener onInformRecyclerViewToScrollDownListener) {
    this.onInformRecyclerViewToScrollDownListener = onInformRecyclerViewToScrollDownListener;
  }

  /**
   * @return
   */
  public OnItemLongClickListener getOnItemLongClickListener() {
    return onItemLongClickListener;
  }

  /**
   * @param onItemLongClickListener
   */
  public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
    this.onItemLongClickListener = onItemLongClickListener;
  }

  /**
   * @return
   */
  public Context getContext() {
    return context;
  }

  /**
   * @param context
   */
  public void setContext(Context context) {
    this.context = context;
  }

  /**
   * @param context
   * @param contactJid
   */
  public ChatMessageAdapter(Context context, String contactJid) {
    this.layoutInflater = LayoutInflater.from(context);
    this.context = context;
    this.contactJid = contactJid;

    chatMessageList = ChatMessagesModel.get(context).getMessages(contactJid);
    Log.d(LOGTAG, "Getting messages for: " + contactJid);
  }

  /**
   *
   */
  public void informRecyclerViewToScrollDown() {
    onInformRecyclerViewToScrollDownListener
        .onInformRecyclerViewToScrollDown(chatMessageList.size());
  }

  /**
   * @param parent
   * @param viewType
   * @return
   */
  @Override
  public ChatMessageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
      int viewType) {

    View itemView;
    switch (viewType) {
      case SENT:
        itemView = layoutInflater.inflate(R.layout.chat_message_sent, parent, false);
        return new ChatMessageViewHolder(itemView, this);

      case RECEIVED:
        itemView = layoutInflater.inflate(R.layout.chat_message_received, parent, false);
        return new ChatMessageViewHolder(itemView, this);

      default:
        itemView = layoutInflater.inflate(R.layout.chat_message_sent, parent, false);
        return new ChatMessageViewHolder(itemView, this);
    }

  }

  /**
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(@NonNull @NotNull ChatMessageViewHolder holder, int position) {
    ChatMessage chatMessage = chatMessageList.get(position);
    holder.bindChat(chatMessage);
  }

  /**
   * @return
   */
  @Override
  public int getItemCount() {
    return chatMessageList.size();
  }

  /**
   * @param position
   * @return
   */
  @Override
  public int getItemViewType(int position) {
    ChatMessage.Type messageType = chatMessageList.get(position).getType();
    if (messageType == Type.SENT) {
      return SENT;
    } else {
      return RECEIVED;
    }
  }

  /**
   *
   */
  public void onMessageAdd() {
    chatMessageList = ChatMessagesModel.get(context).getMessages(contactJid);
    notifyDataSetChanged();
    informRecyclerViewToScrollDown();
  }
}

/**
 *
 */
class ChatMessageViewHolder extends RecyclerView.ViewHolder {

  private static final String LOGTAG = "ChatMessageViewHolder";
  private final TextView messageBody;
  private final TextView messageTimestamp;
  private final ImageView profileImage;
  private ChatMessage mchatMessage;
  private final ChatMessageAdapter mAdapter;

  /**
   * @param itemView
   * @param mAdapter
   */
  public ChatMessageViewHolder(View itemView, final ChatMessageAdapter mAdapter) {
    super(itemView);

    messageBody = itemView.findViewById(R.id.chat_textMessageBody);
    messageTimestamp = itemView.findViewById(R.id.chat_textMessageTimestamp);
    profileImage = itemView.findViewById(R.id.profile);

    this.mAdapter = mAdapter;

    itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        ChatMessageAdapter.OnItemLongClickListener listener = mAdapter.getOnItemLongClickListener();
        if (listener != null) {
          listener.onItemLongClick(mchatMessage.getPersistID(), itemView);
        }
        return false;
      }
    });
  }

  /**
   * @param chatMessage
   */
  public void bindChat(ChatMessage chatMessage) {
    mchatMessage = chatMessage;
    messageBody.setText(chatMessage.getMessage());
    messageTimestamp.setText(Utilities.getFormattedTime(mchatMessage.getTimestamp()));
    profileImage.setImageResource(R.drawable.ic_baseline_person_24);

    ChatMessage.Type type = mchatMessage.getType();

    if (type == Type.RECEIVED) {
      ChatConnection rc = ChatConnectionService.getConnection();
      if (rc != null) {
        String imageAbsPath = rc.getProfileImageAbsolutePath(mchatMessage.getContactJid());
        if (imageAbsPath != null) {
          Drawable d = Drawable.createFromPath(imageAbsPath);
          profileImage.setImageDrawable(d);
        }
      }
    }

    if (type == Type.SENT) {
      ChatConnection rc = ChatConnectionService.getConnection();
      if (rc != null) {
        String selfJid = PreferenceManager.getDefaultSharedPreferences(mAdapter.getContext())
            .getString("xmpp_jid", null);

        if (selfJid != null) {
          Log.d(LOGTAG, "A valid self jid:" + selfJid);
          String imageAbsPath = rc.getProfileImageAbsolutePath(selfJid);
          if (imageAbsPath != null) {
            Drawable d = Drawable.createFromPath(imageAbsPath);
            profileImage.setImageDrawable(d);
          }
        } else {
          Log.d(LOGTAG, "Could not get valid self jid");
        }
      }
    }
  }
}
