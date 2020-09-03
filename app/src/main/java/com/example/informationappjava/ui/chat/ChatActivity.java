package com.example.informationappjava.ui.chat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity implements TextWatcher {

    private String name;
    private WebSocket webSocket;
    private String SERVER_PATH = "";
    private EditText messageEdit;
    private View sendBtn;
    private View pickImageBtn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");
        initateSocketConnection();
    }

    private void initateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, );
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();

        if (string.isEmpty()) {
            resetMessageEdit();
        } else {
            sendBtn.setVisibility(View.VISIBLE);
            pickImageBtn.setVisibility(View.INVISIBLE);
        }

    }

    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);
        messageEdit.setText("");

        sendBtn.setVisibility(View.INVISIBLE);
        pickImageBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);
    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(() -> {
                Toast.makeText(ChatActivity.this, "Socket Connection Successful",
                    Toast.LENGTH_SHORT).show();

                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
        }
    }

    private void initializeView()  {

        messageEdit = findViewById(R.id.chat_messageEdit);
        sendBtn = findViewById(R.id.chat_sendButton);
        pickImageBtn = findViewById(R.id.chat_pickImg);
        recyclerView = findViewById(R.id.chat_recyclerView);

        messageEdit.addTextChangedListener(this);

        sendBtn.setOnClickListener(v -> {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    //Um den Username aus dem vorherigen Ding zu bekommen
    Bundle bundle = getIntent().getExtras();
}