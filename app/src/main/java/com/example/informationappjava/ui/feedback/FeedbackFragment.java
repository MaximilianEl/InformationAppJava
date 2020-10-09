package com.example.informationappjava.ui.feedback;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.informationappjava.R;

public class FeedbackFragment extends Fragment {

  private FeedbackViewModel mViewModel;
  private String mfg;

  public static FeedbackFragment newInstance() {
    return new FeedbackFragment();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_feedback, container, false);
    final EditText name = view.findViewById(R.id.name);
    final EditText subject = view.findViewById(R.id.subject);
    final EditText message = view.findViewById(R.id.message);

    mfg = getResources().getString(R.string.mfg);

    Button send = view.findViewById(R.id.send);
    send.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String subS = subject.getText().toString();
        String mesS = message.getText().toString();
        String namS = name.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"infoappv2@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        email
            .putExtra(Intent.EXTRA_TEXT, message.getText().toString() + "\n\n" + mfg + "\n" + namS);

        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose app to send Mail"));

        subject.getText().clear();
        message.getText().clear();
        name.getText().clear();


      }
    });

    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
    // TODO: Use the ViewModel
  }
}






