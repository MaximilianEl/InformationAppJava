package com.example.informationappjava.ui.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.login.Constants;
import com.example.informationappjava.xmpp.ChatConnection;
import com.example.informationappjava.xmpp.ChatConnectionService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * The MeActivity is the Menu where the current User can see his own Information, like his own Onlinestatus and his
 * Profile picture, and can change his Profile image.
 */
public class MeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOGTAG = "MeActivity";
    private BroadcastReceiver mBroadcastReceiver;
    private TextView connectionStatusTextView;
    private ImageView profileImageView;
    private static final int SELECT_PHOTO = 100;

    /**
     * When the Activity is created it builds a Connection to the Server to get the Onlinestatus of the User.
     * It also receives the Profile image from the specific user.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        String status;
        ChatConnection connection = ChatConnectionService.getConnection();
        connectionStatusTextView = findViewById(R.id.connection_status);

        if (connection != null) {
            status = connection.getConnectionstateString();
            connectionStatusTextView.setText(status);
        }

        profileImageView = findViewById(R.id.profile_image);
        profileImageView.setOnClickListener(this);

        String selfJid = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("xmpp_jid", null);

        ChatConnection chatConnection = ChatConnectionService.getConnection();

        profileImageView.setImageResource(R.drawable.ic_baseline_person_24);
        if (chatConnection != null) {

            String imageAbsPtah = chatConnection.getProfileImageAbsolutePath(selfJid);
            if (imageAbsPtah != null) {

                Drawable drawable = Drawable.createFromPath(imageAbsPtah);
                profileImageView.setImageDrawable(drawable);
            }
        }
    }

    /**
     * Stops the changing of the Online status
     */
    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * Changes the Online status of the User from a Broadcast
     */
    @Override
    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case Constants.BroadCastMessages.UI_CONNECTION_STATUS_CHANGE_FLAG:

                        String status = intent.getStringExtra(Constants.UI_CONNECTION_STATUS_CHANGE);
                        connectionStatusTextView.setText(status);
                        break;
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BroadCastMessages.UI_CONNECTION_STATUS_CHANGE_FLAG);
        this.registerReceiver(mBroadcastReceiver, filter);
    }

    /**
     * Provides the otion to select an Profile image from the Device when clicked on the Profile image
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Log.d(LOGTAG, "Clicked on the profile picture");
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    /**
     * Tests if the selected Image for the Profile picture is really a picture
     * then proceeds to insert the picture into the Users Profile
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Log.d(LOGTAG, "Result is OK");
                    Uri selectedImage = data.getData();

                    Bitmap bm = null;

                    try {
                        bm = decodeUri(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (bm != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.PNG, 0, stream);
                        byte[] byteArray = stream.toByteArray();
                        Log.d(LOGTAG,
                                "Bitmap not NULL, proceeding with setting image. The array size is:" + byteArray);
                        ChatConnection rc = ChatConnectionService.getConnection();
                        if (rc != null) {
                            if (rc.setSelfAvatar(byteArray)) {
                                Log.d(LOGTAG, "Avatar set correctly");

                                Drawable image = new BitmapDrawable(getResources(),
                                        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
                                profileImageView.setImageDrawable(image);
                            } else {
                                Log.d(LOGTAG, "Could not set user avatar");
                            }
                        }
                    }
                } else {
                    Log.d(LOGTAG, "Canceled out the Image selection act");
                }
        }
    }

    /**
     * The Programm decodes Imagedata that were set as profileimages
     *
     * @param selectedImage
     * @return
     * @throws FileNotFoundException
     */
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 140;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory
                .decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }
}