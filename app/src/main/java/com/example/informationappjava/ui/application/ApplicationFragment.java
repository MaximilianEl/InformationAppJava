package com.example.informationappjava.ui.application;

import android.content.Intent;
import android.net.Uri;
import android.view.View.OnClickListener;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.informationappjava.R;

/**
 *The application fragment
 */
public class ApplicationFragment extends Fragment {

    private ApplicationViewModel mViewModel;
    private CardView application;
    private CardView approve;
    private CardView enrol;
    private CardView start;
    private final String applicationString = "application";
    private final String enrolString = "enrol";
    private final String approveString = "approve";
    private final String startString = "start";

    /**
     *The onCreateView() method createes the view for the application fragment.
     *The onCreateView() method contains different onClick() methods.
     *The first onCLick() method gets the ApplicationContextActivity class.
     *The second and third onClick() methods are created to open up different URLs
     *The last onCLick() method puts in the value of the string for the ApplicationContextActivity
     * class.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application, container, false);

        application = view.findViewById(R.id.application_appli);
        application.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ApplicationContextActivity.class);
                intent.putExtra("value", applicationString);
                startActivity(intent);
            }
        });

        approve = view.findViewById(R.id.application_approve);
        approve.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/studium/rund-ums-studium/bewerbung/hochschulzugang/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        enrol = view.findViewById(R.id.application_enrol);
        enrol.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.hs-osnabrueck.de/wir/fakultaeten/mkt/institute/institut-fuer-management-und-technik/erstsemesterinformationen/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        start = view.findViewById(R.id.application_start);
        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ApplicationContextActivity.class);
                intent.putExtra("value", startString);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * This method calls upon the ViewModel for this Fragment
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ApplicationViewModel.class);
    }
}