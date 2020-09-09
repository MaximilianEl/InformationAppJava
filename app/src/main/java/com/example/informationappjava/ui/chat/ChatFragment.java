package com.example.informationappjava.ui.chat;

import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.registration.RegistrationActivity;
import org.w3c.dom.Text;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        TextView registerText = view.findViewById(R.id.chat_register);
        registerText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

    }

    public void openRegister() {
        Intent intent = new Intent(getContext(), RegistrationActivity.class);
        startActivity(intent);
    }
}