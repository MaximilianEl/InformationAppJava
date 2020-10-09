package com.example.informationappjava.ui.institute;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.example.informationappjava.R;

/**
 *
 */
public class InstituteCourseActivity extends AppCompatActivity {

    private CardView cardViewMBau;
    private CardView cardViewBWL;
    private CardView cardViewBInf;
    private CardView cardViewBIng;
    private CardView cardViewMBA;
    private CardView cardViewBIngEne;
    private CardView cardViewMAT;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_courses);

        cardViewMBau = findViewById(R.id.ins_masch);
        cardViewMBau.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/studienangebot/bachelor/allgemeiner-maschinenbau-bsc-standort-lingen-ems/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewBWL = findViewById(R.id.ins_bwl);
        cardViewBWL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/studienangebot/bachelor/betriebswirtschaft-und-management-ba-standort-lingen-ems/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewBInf = findViewById(R.id.ins_infor);
        cardViewBInf.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/studienangebot/bachelor/wirtschaftsinformatik-bsc-standort-lingen-ems/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewBIng = findViewById(R.id.ins_ing);
        cardViewBIng.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/studienangebot/bachelor/wirtschaftsingenieurwesen-bsc-standort-lingen-ems/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewMBA = findViewById(R.id.ins_mba);
        cardViewMBA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/studienangebot/master/wirtschaftsingenieurwesen-mba-berufsbegleitend/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewBIngEne = findViewById(R.id.ins_energy);
        cardViewBIngEne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/studienangebot/master/wirtschaftsingenieurwesen-energiewirtschaft-msc-standort-lingen-ems/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewMAT = findViewById(R.id.ins_mut);
        cardViewMAT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/studienangebot/master/management-und-technik-msc/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}