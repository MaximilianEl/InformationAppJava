package com.example.informationappjava.ui.chat.chatactiv.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import com.example.informationappjava.R;
import com.example.informationappjava.ui.chat.chatlist.ChatListActivity;
import com.example.informationappjava.xmpp.RoosterConnection;
import com.example.informationappjava.xmpp.RoosterConnectionService;

public class ChatLoginActivity extends AppCompatActivity {

    private static final String LOGTAG = "LoginActivity";
    private LoginViewModel loginViewModel;
    private EditText jidEditText;
    private EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jidEditText = findViewById(R.id.jid_login_form);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    jidEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(jidEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        jidEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(jidEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(jidEditText.getText().toString(),
                        passwordEditText.getText().toString());
                Intent i = new Intent(ChatLoginActivity.this, ChatListActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void saveCredentialsAndLogin() {
        Log.d(LOGTAG, "saveCredentialsAndLogin() called");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit()
            .putString("xmpp_jid", jidEditText.getText().toString())
            .putString("xmpp_password", passwordEditText.getText().toString())
            .commit();

        Intent intent = new Intent(this, RoosterConnectionService.class);
        startService(intent);

    }
}