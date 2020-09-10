package com.example.informationappjava.ui.chat;

import android.content.Intent;
import android.net.Uri;
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

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;
    private TextView registerText;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        registerText = view.findViewById(R.id.openRegister);
        registerText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://sep-01.lin.hs-osnabrueck.de:9090/plugins/registration/sign-up.jsp";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);

    }
}