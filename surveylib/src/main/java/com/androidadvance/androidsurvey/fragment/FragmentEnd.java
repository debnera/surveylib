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

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.R;
import com.androidadvance.androidsurvey.SurveyActivity;
import com.androidadvance.androidsurvey.models.SurveyProperties;

public class FragmentEnd extends Fragment {

    private FragmentActivity activity;
    private TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
        		R.layout.startend, container, false);

        Button finishButton = (Button) rootView.findViewById(R.id.button_continue);
        message = (TextView) rootView.findViewById(R.id.textView_message);
	    finishButton.setText(R.string.finish_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishSurvey();
            }
        });

        return rootView;
    }

    private void finishSurvey() {
	    ((SurveyActivity) activity).eventSurveyCompleted(Answers.getInstance());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();
        SurveyProperties surveryProperties = (SurveyProperties) getArguments().getSerializable("survey_properties");

        if (surveryProperties != null) {
	        message.setText(Html.fromHtml(surveryProperties.getEndMessage()));
        }
    }
}