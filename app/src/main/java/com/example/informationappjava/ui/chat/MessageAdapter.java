package com.example.informationappjava.ui.chat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageAdapter extends RecyclerView.Adapter {

  private static final int TYPE_MESSAGE_SENT = 0;
  private static final int TYPE_MESSAGE_RECEIVED = 1;
  private static final int TYPE_IMAGE_SENT = 2;
  private static final int TYPE_IMAGE_RECEIVED = 3;

  private LayoutInflater inflater;
  private List<JSONObject> messages = new ArrayList<>();

  public MessageAdapter (LayoutInflater inflater) {
    this.inflater = inflater;
  }

  private class SentMessageHolder extends RecyclerView.ViewHolder {

    TextView messageTxt;

    public SentMessageHolder(@NonNull @NotNull View itemView) {
      super(itemView);

      messageTxt = itemView.findViewById(R.id.chat_sentMessage);
    }
  }

  private class SentImageHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public SentImageHolder(@NonNull @NotNull View itemView) {
      super(itemView);

      imageView = itemView.findViewById(R.id.chat_sentImage);
    }
  }

  private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView messageTxt;

    public ReceivedMessageHolder(@NonNull @NotNull View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.chat_nameTxt);
      messageTxt = itemView.findViewById(R.id.chat_receivedText);
    }
  }

  private class ReceivedImageHolder extends RecyclerView.ViewHolder {

    TextView name;
    ImageView imageView;

    public ReceivedImageHolder(@NonNull @NotNull View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.chat_nameTxt);
      imageView = itemView.findViewById(R.id.chat_receivedImage);
    }
  }

  @Override
  public int getItemViewType(int position) {

    JSONObject message = messages.get(position);

    try {
      if (message.getBoolean("isSent")){

        if (message.has("message")) {
          return TYPE_MESSAGE_SENT;
        } else {
          return TYPE_IMAGE_SENT;
        }
      } else {

        if (message.has("message")) {
          return TYPE_MESSAGE_RECEIVED;
        } else {
          return TYPE_IMAGE_RECEIVED;
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return -1;
  }

  @NonNull
  @NotNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent,
      int viewType) {

    View view;

    switch (viewType) {
      case TYPE_IMAGE_SENT:

        view = inflater.inflate(R.layout.item_sent_image, parent, false);
        return new SentImageHolder(view);

      case TYPE_MESSAGE_SENT:

        view = inflater.inflate(R.layout.item_sent_message, parent, false);
        return new SentMessageHolder(view);

      case TYPE_IMAGE_RECEIVED:

        view = inflater.inflate(R.layout.item_received_image, parent, false);
        return new ReceivedImageHolder(view);

      case TYPE_MESSAGE_RECEIVED:

        view = inflater.inflate(R.layout.item_received_message, parent, false);
        return new ReceivedMessageHolder(view);

    }
    return null;
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

    JSONObject message = messages.get(position);

    try {
      if (message.getBoolean("isSent")) {

        if (message.has("message")){

          SentMessageHolder sentMessageHolder = (SentMessageHolder) holder;
          sentMessageHolder.messageTxt.setText(message.getString("message"));
        } else {

          SentImageHolder sentImageHolder = (SentImageHolder) holder;
          Bitmap bitmap = getBitmapFromString(message.getString("image"));

          sentImageHolder.imageView.setImageBitmap(bitmap);
        }

      } else {

        if (message.has("message")) {

          ReceivedMessageHolder receivedMessageHolder = (ReceivedMessageHolder) holder;
          receivedMessageHolder.name.setText(message.getString("name"));
          receivedMessageHolder.messageTxt.setText(message.getString("message"));

        } else {

          ReceivedImageHolder receivedImageHolder = (ReceivedImageHolder) holder;
          receivedImageHolder.name.setText(message.getString("name"));

          Bitmap bitmap = getBitmapFromString(message.getString("image"));
          receivedImageHolder.imageView.setImageBitmap(bitmap);
        }

      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private Bitmap getBitmapFromString(String image) {
    byte[] bytes = Base64.decode(image, Base64.DEFAULT);
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
  }

  @Override
  public int getItemCount() {
    return messages.size();
  }

  public void addItem(JSONObject jsonObject) {
    messages.add(jsonObject);
    notifyDataSetChanged();
  }
}
