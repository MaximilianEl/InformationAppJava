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
import com.example.informationappjava.xmpp.ChatConnection;
import com.example.informationappjava.xmpp.ChatConnectionService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The ChatListAdapter class enables the Chatlist to be displayed on the ChatView.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatHolder> {

    private static final String LOGTAG = "ChatListAdapter";
    List<Chat> chatList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClick;
    private final Context mContext;

    /**
     * This is an Interface that enables an onclick event.
     */
    public interface OnItemClickListener {
        void onItemClick(String contactJid, Chat.ContactType chatType);
    }

    /**
     * This is an Interface that enables an onlongclick event.
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(String contactJod, int chatUniqueId, View anchor);
    }

    /**
     * This is a Constructor to call upon the ChatListAdapter class.
     *
     * @param context
     */
    public ChatListAdapter(Context context) {
        this.chatList = ChatModel.get(context).getChats();
        this.mContext = context;
    }

    /**
     * This is a getter for the click event.
     *
     * @return
     */
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    /**
     * This is a setter for the click event.
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * This is a getter for the longclick event.
     *
     * @return
     */
    public OnItemLongClickListener getOnItemLongClick() {
        return onItemLongClick;
    }

    /**
     * This is a setter for the longclick event.
     *
     * @param onItemLongClick
     */
    public void setOnItemLongClick(OnItemLongClickListener onItemLongClick) {
        this.onItemLongClick = onItemLongClick;
    }

    /**
     * This function inflates the chat_list_item view and returns the Chatholder holding the chats.
     *
     * @param parent
     * @param viewType
     * @return new ChatHolder
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
     * This function binds a specific chat with the holder.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.bindChat(chat);
    }

    /**
     * This function returns the size of the current chatlist.
     *
     * @return chatList.size
     */
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     * This function notifies the View that the Chatsize has been changed.
     */
    public void onChatCountChange() {
        chatList = ChatModel.get(mContext).getChats();
        notifyDataSetChanged();
        Log.d(LOGTAG, "ChatListAdapter knows of the change in message");
    }
}

/**
 * The ChatHolder class gives Informations to the ChatViews inside the Chatlist.
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
     * This is a constructor to call upon the ChatHolder and it also sets the onclick events for the Chats.
     *
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
                    listener.onItemLongClick(mChat.getJid(), mChat.getPersistID(), itemView);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * This function gives out the Information for the Chats in the Chatlist View.
     * The Informations include the last message, the timestamp of the last Message and the profile picture of the
     * specific user.
     *
     * @param chat
     */
    public void bindChat(Chat chat) {
        mChat = chat;
        contactTextView.setText(chat.getJid());
        messageAbstractTextview.setText(chat.getLastMessage());
        timestampTextView.setText(Utilities.getFormattedTime(mChat.getLastMessageTimeStamp()));

        profileImage.setImageResource(R.drawable.ic_baseline_person_24);

        ChatConnection rc = ChatConnectionService.getConnection();
        if (rc != null) {
            String imageAbsPath = rc.getProfileImageAbsolutePath(mChat.getJid());
            if (imageAbsPath != null) {
                Drawable d = Drawable.createFromPath(imageAbsPath);
                profileImage.setImageDrawable(d);
            }
        }
    }
}