package com.example.informationappjava.ui.chat.chatlist.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.chatactiv.login.model.Chat;
import com.example.informationappjava.ui.chat.chatactiv.login.model.ChatModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatHolder> {

    public interface OnItemClickListener {
        public void onItemClick(String contactJid);
    }

    List<Chat> chatList;
    private OnItemClickListener onItemClickListener;

    public ChatListAdapter(Context context) {
        this.chatList = ChatModel.get(context).getChats();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(
        OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_list_item, parent, false);

        return new ChatHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.bindChat(chat);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}

class ChatHolder extends RecyclerView.ViewHolder {

    private static final String LOGTAG = "ChatHolder";
    private TextView contactTextView;
    private TextView messageAbstractTextview;
    private TextView timestampTextView;
    private ImageView profileImage;
    private Chat mChat;
    private ChatListAdapter chatListAdapter;

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
                    listener.onItemClick(contactTextView.getText().toString());

                }

                Log.d(LOGTAG, "Clicked on the item in the recyclerView");

            }
        });
    }

    public void bindChat(Chat chat) {
        contactTextView.setText(chat.getJid());
        messageAbstractTextview.setText(chat.getLastMessage());
        profileImage.setImageResource(R.drawable.ic_baseline_person_24);
        timestampTextView.setText("12:00 AM");
    }


}