package com.example.informationappjava.ui.chat.contacts.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.contacts.model.Contact;
import com.example.informationappjava.ui.chat.contacts.model.ContactModel;
import com.example.informationappjava.xmpp.ChatConnection;
import com.example.informationappjava.xmpp.ChatConnectionService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactHolder> {

    private List<Contact> mContacts;
    private final Context mContext;
    private static final String LOGTAG = "ContactListAdapter";
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * This Interface creates an onItemClickListener.
     */
    public interface OnItemClickListener {
        void onItemClick(String contactJid);
    }

    /**
     * This Interface creates an onItemLongClickListener.
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(int uniqueId, String contactJid, View anchor);
    }

    /**
     * This is a Constructor to call upon ContactListAdapter class.
     *
     * @param context
     */
    public ContactListAdapter(Context context) {
        mContacts = ContactModel.get(context).getContacts();
        mContext = context;
        Log.d(LOGTAG, "Constructor of ChatListAdpater, the size of the backing list is:" + mContacts.size());
    }

    /**
     * This is a getter for the OnItemClick event.
     *
     * @return mOnItemClickListener
     */
    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    /**
     * This is a setter for the OnItemClick event.
     *
     * @param mOnItemClickListener
     */
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * This is a getter for the OnItemLongClick event.
     *
     * @return mOnItemLongClickListener
     */
    public OnItemLongClickListener getmOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    /**
     * This is a setter for the OnItemLongClick event.
     *
     * @param mOnItemLongClickListener
     */
    public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    /**
     * This functuin inflates the contact_list_item view.
     *
     * @param parent
     * @param viewType
     * @return new ContactHolder
     */
    @NonNull
    @NotNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactHolder(view, this);
    }

    /**
     * This function binds a specific Contact with the holder.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull @NotNull ContactHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.bindContact(contact);
    }

    /**
     * This function returns the size of the current Contactlist.
     *
     * @return mContacts.size
     */
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * This function notifies the View that the Contact count has been changed.
     */
    public void onContactCountChange() {
        mContacts = ContactModel.get(mContext).getContacts();
        notifyDataSetChanged();
        Log.d(LOGTAG, "ContactListAdapter knows of the change in messages");
    }

    /**
     * The ChatHolder class gives Informations to the ContactViews inside the Contactlist.
     */
    class ContactHolder extends RecyclerView.ViewHolder {

        private static final String LOGTAG = "ContactHolder";
        private final TextView jidTextView;
        private final TextView subscriptionTypeTextView;
        private Contact mContact;
        private final ImageView profile_image;
        private final ContactListAdapter mAdapter;

        /**
         * This is a constructor to call upon the ContactHolder and it also sets the onclick events for the Contacts.
         *
         * @param itemView
         * @param adapter
         */
        public ContactHolder(@NonNull @NotNull final View itemView, ContactListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            jidTextView = itemView.findViewById(R.id.contact_jid_string);
            subscriptionTypeTextView = itemView.findViewById(R.id.subscription_type);
            profile_image = itemView.findViewById(R.id.profile_contact);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(LOGTAG, "User clicked on Contact Item");
                    ContactListAdapter.OnItemClickListener listener = mAdapter.getmOnItemClickListener();
                    if (listener != null) {
                        Log.d(LOGTAG, "Calling the listener method");
                        listener.onItemClick(jidTextView.getText().toString());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ContactListAdapter.OnItemLongClickListener listener = mAdapter
                            .getmOnItemLongClickListener();
                    if (listener != null) {
                        mAdapter.getmOnItemLongClickListener()
                                .onItemLongClick(mContact.getPersistID(), mContact.getJid(), itemView);
                        return true;
                    }
                    return false;
                }
            });
        }

        /**
         * This function gives out the Informations of the Contacts in the Contactlist View.
         * The Informations include the Contactjid, subscriptiontype and the profile picture of the
         * specific user.
         *
         * @param c
         */
        void bindContact(Contact c) {
            mContact = c;
            if (mContact == null) {
                return;
            }
            jidTextView.setText(mContact.getJid());
            subscriptionTypeTextView.setText("NONE_NONE");
            profile_image.setImageResource(R.drawable.ic_baseline_person_24);

            ChatConnection rc = ChatConnectionService.getConnection();
            if (rc != null) {
                String imageAbsPath = rc.getProfileImageAbsolutePath(mContact.getJid());
                if (imageAbsPath != null) {
                    Drawable d = Drawable.createFromPath(imageAbsPath);
                    profile_image.setImageDrawable(d);
                }
            }
        }

    }
}