package com.example.informationappjava.ui.application;

import android.content.Intent;
import android.net.Uri;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.example.informationappjava.R;

/**
 *AplicationContextActivity class
 */
public class ApplicationContextActivity extends AppCompatActivity {

  private CardView cardViewLeft;
  private CardView cardViewRight;
  private CardView cardViewCenter;
  private TextView applicationHeader;
  private TextView applicationContext;
  private TextView leftTextView;
  private TextView centerTextView;
  private TextView rightTextView;
  private ImageView leftImageView;
  private ImageView centerImageView;
  private ImageView rightImageView;
  private final String applicationString = "application";
  private final String enrolString = "enrol";
  private final String approveString = "approve";
  private final String startString = "start";
  private String[] applicationContextArray;
  private String[] applicationHeaderArray;
  private String[] applicationButtonArray;

  /**
   * The if statement is used to fill the strings depending on the different string input.
   * The onCreateView() method contains different onClick() methods.
   * The onClick() methods are created to open up different URLs.
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_application_context);

    Intent intent = getIntent();
    String s1 = intent.getStringExtra("value");

    applicationHeaderArray = getResources().getStringArray(R.array.application_header_array);
    applicationContextArray = getResources().getStringArray(R.array.application_context_array);
    applicationButtonArray = getResources().getStringArray(R.array.application_button_array);

    applicationHeader = findViewById(R.id.application_header);
    applicationContext = findViewById(R.id.application_context);
    cardViewLeft = findViewById(R.id.application_card_left);
    cardViewCenter = findViewById(R.id.application_card_center);
    cardViewRight = findViewById(R.id.application_card_right);
    leftTextView = findViewById(R.id.left_text);
    centerTextView = findViewById(R.id.center_text);
    rightTextView = findViewById(R.id.right_text);
    leftImageView = findViewById(R.id.appli_card_img_left);
    centerImageView = findViewById(R.id.appli_card_img_center);
    rightImageView = findViewById(R.id.appli_card_img_right);

    applicationContext.setMovementMethod(new ScrollingMovementMethod());

    if (s1.equals(applicationString)) {

      applicationHeader.setText(applicationHeaderArray[0]);
      applicationContext.setText(applicationContextArray[0]);
      leftTextView.setText(applicationButtonArray[0]);
      rightTextView.setText(applicationButtonArray[1]);

      rightImageView.setImageResource(R.drawable.ic_outline_info_24);
      cardViewCenter.setVisibility(View.GONE);

      cardViewLeft.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          String url = "https://osca-bew.hs-osnabrueck.de/scripts/mgrqispi.dll?APPNAME=CampusNet&PRGNAME=EXTERNALPAGES&ARGUMENTS=-N000000000000001,-N000186,-Ahsoswelcome";
          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          startActivity(i);
        }
      });

      cardViewRight.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          String url = "https://www.hs-osnabrueck.de/studium/rund-ums-studium/bewerbung/";
          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          startActivity(i);
        }
      });
    } else if (s1.equals(approveString)) {

      applicationHeader.setText(applicationHeaderArray[1]);
      applicationContext.setText(applicationContextArray[1]);
      cardViewLeft.setVisibility(View.GONE);
      leftTextView.setText(applicationButtonArray[2]);
      rightTextView.setText(applicationButtonArray[3]);
      cardViewCenter.setVisibility(View.GONE);
    } else if (s1.equals(enrolString)) {

      applicationHeader.setText(applicationHeaderArray[2]);
      applicationContext.setText(applicationContextArray[2]);
      cardViewLeft.setVisibility(View.GONE);
      leftTextView.setVisibility(View.GONE);
      rightTextView.setText(applicationButtonArray[5]);
      cardViewCenter.setVisibility(View.GONE);
    } else {

      applicationHeader.setText(applicationHeaderArray[3]);
      applicationContext.setText(applicationContextArray[3]);
      leftTextView.setText(applicationButtonArray[6]);
      centerTextView.setText(applicationButtonArray[7]);
      rightTextView.setText(applicationButtonArray[8]);

      cardViewLeft.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/startnow/";
          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          startActivity(i);
        }
      });

      cardViewCenter.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/erstsemesterinformationen/";
          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          startActivity(i);
        }
      });

      cardViewRight.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/startnow/#c9915872";
          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          startActivity(i);
        }
      });
    }
  }
}