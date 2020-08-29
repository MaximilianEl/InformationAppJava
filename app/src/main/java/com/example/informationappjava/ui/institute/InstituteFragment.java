package com.example.informationappjava.ui.institute;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.informationappjava.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class InstituteFragment extends Fragment {

    private InstituteViewModel instituteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        instituteViewModel =
                ViewModelProviders.of(this).get(InstituteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_institute, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        instituteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        getInsData(root);
        final TextView secondtext = root.findViewById(R.id.ins_secondtext);
        secondtext.setOnClickListener(ins_secondclick);
        return root;
    }

    private View.OnClickListener ins_secondclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView ins_secondtext = view.findViewById(R.id.ins_secondtext);
            TextView ins_header2 = view.findViewById(R.id.ins_header2);

            if (ins_secondtext.length() <= 0) {
                ins_header2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                ins_secondtext.setVisibility(View.VISIBLE);
                fillTextTwo(view);
            } else {
                ins_header2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                ins_secondtext.setText("");
                ins_secondtext.setVisibility(View.GONE);
            }
        }
    };

    private View.OnClickListener ins_thirdclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TextView ins_thirdtext = view.findViewById(R.id.ins_thirdtext);
            TextView ins_header3 = view.findViewById(R.id.ins_header3);

            if (ins_thirdtext.length() <= 0) {
                ins_header3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                ins_thirdtext.setVisibility(View.VISIBLE);
                fillTextThree(view);
            } else {
                ins_header3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                ins_thirdtext.setText("");
                ins_thirdtext.setVisibility(View.GONE);
            }
        }
    };

    static void getInsData(View view) {
        String url =
                "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/#c8477468";
        Connection connect = Jsoup.connect(url);

        Document doc = (Document) connect;

        Elements text = doc.getElementsByTag("p");

        Elements header = doc.select("a[data-toggle]");

        String firsttext = text.get(0).text();
        String secondtext = text.get(1).text();
        String thirdtext = text.get(2).text();
        String fourthtext = text.get(3).text();
        String fulltext = firsttext + secondtext + thirdtext + fourthtext;

        TextView insText = view.findViewById(R.id.insText);
        TextView ins_header1 = view.findViewById(R.id.ins_header1);
        TextView ins_header2 = view.findViewById(R.id.ins_header2);
        TextView ins_header3 = view.findViewById(R.id.ins_header3);

        insText.setText(fulltext);
        ins_header1.setText(header.get(0).text());
        ins_header2.setText(header.get(1).text());
        ins_header3.setText(header.get(2).text());
    }

    static void fillTextTwo(View view) {
        String url =
                "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/#c8477468";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements p = doc.getElementsByTag("p");

        String text1 = p.get(4).text();
        String text2 = p.get(5).text();
        String text3 = p.get(6).text();
        String fulltext = text1 + text2 + text3;

        TextView ins_secondtext = view.findViewById(R.id.ins_secondtext);

        ins_secondtext.setText(fulltext);
    }

    static void fillTextThree(View view) {
        String url =
                "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/#c8477468";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Elements p = doc.getElementsByTag("p");

        String text1 = p.get(8).text();
        String text2 = p.get(9).text();
        String text3 = p.get(10).text();
        String fulltext = text1 + " " + text2 + " " + text3;

        TextView ins_thirdtext = view.findViewById(R.id.ins_thirdtext);

        ins_thirdtext.setText(fulltext);
    }
}