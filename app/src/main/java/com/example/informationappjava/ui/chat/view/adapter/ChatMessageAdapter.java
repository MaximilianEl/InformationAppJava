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
import com.example.informationappjava.ui.chat.Utilities;
import com.example.informationappjava.ui.chat.login.model.Chat;
import com.example.informationappjava.ui.chat.view.model.ChatMessage;
import com.example.informationappjava.ui.chat.view.model.ChatMessage.Type;
import com.example.informationappjava.ui.chat.view.model.ChatMessagesModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageViewHolder> {

    public interface OnInformRecyclerViewToScrollDownListener {

        public void onInformRecyclerViewToScrollDown(int size);
    }

    public interface OnItemLongClickListener {
        public void onItemLongClick(int uniqueId, View anchor);
    }

    private static final int SENT = 1;
    private static final int RECEIVED = 2;
    private static final String LOGTAG = "ChatMessageAdapter";

    private List<ChatMessage> chatMessageList;
    private LayoutInflater layoutInflater;
    private Context context;
    private String contactJid;
    private OnInformRecyclerViewToScrollDownListener onInformRecyclerViewToScrollDownListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnInformRecyclerViewToScrollDownListener(
            OnInformRecyclerViewToScrollDownListener onInformRecyclerViewToScrollDownListener) {
        this.onInformRecyclerViewToScrollDownListener = onInformRecyclerViewToScrollDownListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public ChatMessageAdapter(Context context, String contactJid) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.contactJid = contactJid;

        chatMessageList = ChatMessagesModel.get(context).getMessages(contactJid);

    }

    public void informRecyclerViewToScrollDown() {
        onInformRecyclerViewToScrollDownListener
                .onInformRecyclerViewToScrollDown(chatMessageList.size());
    }

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
        if (messageType == Type.SENT) {
            return SENT;
        } else {
            return RECEIVED;
        }
    }

    public void onMessageAdd() {
        chatMessageList = ChatMessagesModel.get(context).getMessages(contactJid);
        notifyDataSetChanged();
        informRecyclerViewToScrollDown();
    }
}

class ChatMessageViewHolder extends RecyclerView.ViewHolder {

    private TextView messageBody;
    private TextView messageTimestamp;
    private ImageView profileImage;
    private ChatMessage mchatMessage;

    public ChatMessageViewHolder(View itemView, final ChatMessageAdapter mAdapter) {
        super(itemView);

        messageBody = itemView.findViewById(R.id.chat_textMessageBody);
        messageTimestamp = itemView.findViewById(R.id.chat_textMessageTimestamp);
        profileImage = itemView.findViewById(R.id.profile);

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

    public void bindChat(ChatMessage chatMessage) {
        mchatMessage = chatMessage;
        messageBody.setText(chatMessage.getMessage());
        messageTimestamp.setText(Utilities.getFormattedTime(chatMessage.getTimestamp()));
        profileImage.setImageResource(R.drawable.ic_baseline_person_24);
    }
}
