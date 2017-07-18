package com.androidadvance.androidsurvey.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidadvance.androidsurvey.R;
import com.androidadvance.androidsurvey.SurveyActivity;


/**
 * Created by debnera1 on 7/18/17.
 */

abstract public class QuestionFragment extends Fragment {
	private FragmentActivity activity;
	private TextView title;
	Button continueButton;
	LinearLayout questionContainer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.question_fragment, container, false);

		questionContainer = (LinearLayout) rootView.findViewById(R.id.question_container);
		title = (TextView) rootView.findViewById(R.id.question_title);
		continueButton = (Button) rootView.findViewById(R.id.button_continue);
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				continueAction();
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		getData(getArguments());
		createQuestionContent();
		setTitle();
	}

	private void continueAction() {
		saveAnswer();
		((SurveyActivity) activity).goToNext();
	}

	abstract public void createQuestionContent();

	abstract public void getData(Bundle arguments);

	abstract public void setTitle();

	abstract public void saveAnswer();
}
