package com.example.informationappjava.ui.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import java.io.IOException;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        TextView registerText = view.findViewById(R.id.openRegister);
        registerText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = view.findViewById(R.id.chat_enterButton);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
    }

    private class MyLoginTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword("Hansi", "123")
                .setHost("131.173.65.146")
                .setSecurityMode(SecurityMode.disabled)
                .setPort(5222)
                .setDebuggerEnabled(true)
                .build();

            AbstractXMPPConnection connection = new XMPPTCPConnection(configuration);

            try {
                connection.connect();
                if (connection.isConnected()) {
                    Log.w("app", "Connection done");
                }

                connection.login();

                if (connection.isAuthenticated()) {
                    Log.w("app", "Authentication done");
                }
            } catch (IOException | InterruptedException | XMPPException | SmackException e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}