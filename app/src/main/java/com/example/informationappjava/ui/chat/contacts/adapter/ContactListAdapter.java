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
import com.example.informationappjava.xmpp.RoosterConnection;
import com.example.informationappjava.xmpp.RoosterConnectionService;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 *
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactHolder> {

  /**
   *
   */
  public interface OnItemClickListener {

    void onItemClick(String contactJid);
  }

  /**
   *
   */
  public interface OnItemLongClickListener {

    void onItemLongClick(int uniqueId, String contactJid, View anchor);
  }

  private List<Contact> mContacts;
  private final Context mContext;
  private static final String LOGTAG = "ContactListAdapter";
  private OnItemClickListener mOnItemClickListener;
  private OnItemLongClickListener mOnItemLongClickListener;

  /**
   * @param context
   */
  public ContactListAdapter(Context context) {
    mContacts = ContactModel.get(context).getContacts();
    mContext = context;
    Log.d(LOGTAG,
        "Constructor of ChatListAdpater, the size of the backing list is:" + mContacts.size());
  }

  /**
   * @return
   */
  public OnItemClickListener getmOnItemClickListener() {
    return mOnItemClickListener;
  }

  /**
   * @param mOnItemClickListener
   */
  public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
    this.mOnItemClickListener = mOnItemClickListener;
  }

  /**
   * @return
   */
  public OnItemLongClickListener getmOnItemLongClickListener() {
    return mOnItemLongClickListener;
  }

  /**
   * @param mOnItemLongClickListener
   */
  public void setmOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
    this.mOnItemLongClickListener = mOnItemLongClickListener;
  }

  /**
   * @param parent
   * @param viewType
   * @return
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
   * @param holder
   * @param position
   */
  @Override
  public void onBindViewHolder(@NonNull @NotNull ContactHolder holder, int position) {
    Contact contact = mContacts.get(position);
    holder.bindContact(contact);
  }

  /**
   * @return
   */
  @Override
  public int getItemCount() {
    return mContacts.size();
  }

  /**
   *
   */
  public void onContactCountChange() {
    mContacts = ContactModel.get(mContext).getContacts();
    notifyDataSetChanged();
    Log.d(LOGTAG, "ContactListAdapter knows of the change in messages");
  }

  /**
   *
   */
  class ContactHolder extends RecyclerView.ViewHolder {

    private static final String LOGTAG = "ContactHolder";
    private final TextView jidTextView;
    private final TextView subscriptionTypeTextView;
    private Contact mContact;
    private final ImageView profile_image;
    private final ContactListAdapter mAdapter;


    /**
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

      RoosterConnection rc = RoosterConnectionService.getConnection();
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