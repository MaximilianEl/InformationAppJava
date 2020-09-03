package com.example.informationappjava.ui.chat;

import android.content.Intent;
import android.widget.EditText;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.informationappjava.R;
import java.util.Objects;

public class ChatFragment extends Fragment {

  private ChatViewModel mViewModel;

  public static ChatFragment newInstance() {
    return new ChatFragment();
  }

  @Override
  public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_campus, container, false);
    final EditText editText = view.findViewById(R.id.chat_enterName);
    view.findViewById(R.id.chat_enterButton).setOnClickListener(v -> {
      Intent intent = new Intent(this, ChatActivity.class);
      intent.putExtra("name", editText.getText().toString());
      startActivity(intent);
    });
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
    // TODO: Use the ViewModel

  }

}