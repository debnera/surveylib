package com.androidadvance.androidsurvey.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidadvance.androidsurvey.R;
import com.androidadvance.androidsurvey.SurveyActivity;
import com.androidadvance.androidsurvey.models.SurveyProperties;


public class FragmentStart extends Fragment {

    private FragmentActivity activity;
    private TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.startend, container, false);

        message = (TextView) rootView.findViewById(R.id.textView_message);
        Button continueButton = (Button) rootView.findViewById(R.id.button_continue);
	    continueButton.setText(R.string.start_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SurveyActivity) activity).goToNext();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();
        SurveyProperties surveyProperties = (SurveyProperties) getArguments().getSerializable("survey_properties");

        assert surveyProperties != null;
        message.setText(Html.fromHtml(surveyProperties.getIntroMessage()));




    }
}