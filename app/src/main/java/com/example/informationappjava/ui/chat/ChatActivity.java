package com.example.informationappjava.ui.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informationappjava.R;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private String SERVER_PATH = "ws://echo.websocket.org";
    private EditText messageEdit;
    private View sendBtn;
    private View pickImageBtn;
    private RecyclerView recyclerView;
    private int IMAGE_REQUEST_ID;
    private MessageAdapter messageAdapter;

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
        webSocket = client.newWebSocket(request, new SocketListener());
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

            runOnUiThread(() -> {

                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent", false);
                    messageAdapter.addItem(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void initializeView()  {

        messageEdit = findViewById(R.id.chat_messageEdit);
        sendBtn = findViewById(R.id.chat_sendButton);
        pickImageBtn = findViewById(R.id.chat_pickImg);
        recyclerView = findViewById(R.id.chat_recyclerView);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageEdit.addTextChangedListener(this);

        sendBtn.setOnClickListener(v -> {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("message", messageEdit.getText().toString());

                webSocket.send(jsonObject.toString());
                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);

                resetMessageEdit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        pickImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "Pick Image"),
                IMAGE_REQUEST_ID);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
        @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(inputStream);

                sendImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendImage(Bitmap image) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

        String base64String = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("image", base64String);

            webSocket.send(jsonObject.toString());

            jsonObject.put("isSent", true);
            messageAdapter.addItem(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Um den Username aus dem vorherigen Ding zu bekommen
    Bundle bundle = getIntent().getExtras();
}