package com.example.informationappjava.ui.chat.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.preference.PreferenceManager;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.chatlist.ChatListActivity;
import com.example.informationappjava.ui.chat.login.Constants.BroadCastMessages;
import com.example.informationappjava.xmpp.RoosterConnectionService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private static final String LOGTAG = "RoosterPlus";

    // UI references.
    private AutoCompleteTextView mjidView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);
        // Set up the login form.
        mjidView = (AutoCompleteTextView) findViewById(R.id.jid);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.jid_sign_in || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mjidSignInButton = findViewById(R.id.jid_sign_in);
        mjidSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.loading);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
              String action = intent.getAction();
              switch (action) {
                  case BroadCastMessages.UI_AUTHENTICATED:
                      Log.d(LOGTAG, "Got a broadcast to show the main app window");
                      showProgress(false);
                      Intent intent1 = new Intent(getApplicationContext(), ChatListActivity.class);
                      startActivity(intent1);
                      finish();
                      break;

                  case BroadCastMessages.UI_CONNECTION_ERROR:
                      Log.d(LOGTAG, "Got Connection Error in login activity");
                      showProgress(false);
                      mjidView.setError("Something went wrong while connection. Make sure the credentials are valid and try again.");

                      break;
              }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastMessages.UI_AUTHENTICATED);
        filter.addAction(BroadCastMessages.UI_CONNECTION_ERROR);
        this.registerReceiver(broadcastReceiver, filter);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid jid, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mjidView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String jid = mjidView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid jid address.
        if (TextUtils.isEmpty(jid)) {
            mjidView.setError(getString(R.string.invalid_jid));
            focusView = mjidView;
            cancel = true;
        } else if (!isjidValid(jid)) {
            mjidView.setError(getString(R.string.invalid_jid));
            focusView = mjidView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            saveCredentialsAndLogin();
        }
    }

    private boolean isjidValid(String jid) {
        //TODO: Replace this with your own logic
        return jid.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void saveCredentialsAndLogin() {
        Log.d(LOGTAG, "saveCredentialsAndLogin() called.");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit()
            .putString("xmpp_jid", mjidView.getText().toString())
            .putString("xmpp_password", mPasswordView.getText().toString())
            .commit();

        Intent intent = new Intent(this, RoosterConnectionService.class);
        startService(intent);
    }
}

