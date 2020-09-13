package com.example.informationappjava.ui.chat.contactlist.adapter;

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
import com.example.informationappjava.ui.chat.chatlist.model.Contact;
import com.example.informationappjava.ui.chat.chatlist.model.ContactModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactHolder> {

    public interface OnItemClickListener{
        public void onItemClick(String contactJid);
    }

    private List<Contact> mContacts;
    private Context mContext;
    private static final String LOGTAG = "ContactListAdapter";
    private OnItemClickListener mOnItemClickListener;

    public ContactListAdapter(Context context) {
        mContacts = ContactModel.get(context).getContacts();
        mContext = context;
        Log.d(LOGTAG, "Constructor of ChatListAdpater, the size of the backing list is:" + mContacts.size());
    }

    public OnItemClickListener getmOnItemClickListener(){
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContactHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.bindContact(contact);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}

class ContactHolder extends RecyclerView.ViewHolder {

    private static final String LOGTAG = "ContactHolder";
    private TextView jidTextView;
    private TextView subscriptionTypeTextView;
    private Contact mContact;
    private ImageView profile_image;
    private ContactListAdapter mAdapter;


    public ContactHolder(@NonNull @NotNull View itemView, ContactListAdapter adapter) {
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
                if(listener != null){
                    Log.d(LOGTAG, "Calling the listener method");
                    listener.onItemClick(jidTextView.getText().toString());
                }
            }
        });
    }

    void bindContact(Contact c) {
        mContact = c;
        if (mContact == null) {
            return;
        }
        jidTextView.setText(mContact.getJid());
        subscriptionTypeTextView.setText("NONE_NONE");
        profile_image.setImageResource(R.drawable.ic_baseline_person_24);
    }

}