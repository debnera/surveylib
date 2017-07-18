package com.androidadvance.androidsurvey.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.models.Question;

import java.util.ArrayList;

public class FragmentRadioboxes extends QuestionFragment {

    private Question question;
    private RadioGroup radioGroup;

	public void createQuestionContent() {
		radioGroup = new RadioGroup(activity);
		for (String choice : question.getChoices()) {
			RadioButton rb = new RadioButton(activity);
			rb.setText(Html.fromHtml(choice));
			rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			rb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			radioGroup.addView(rb);
		}
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
				updateContinueButtonVisibility();
			}
		});
		questionContainer.addView(radioGroup);
	}

	@Override
	public boolean isContinueButtonVisible() {
		return !question.getRequired() || isAtleastOneOptionChecked();
	}

	private boolean isAtleastOneOptionChecked() {
		return radioGroup.getCheckedRadioButtonId() != -1;
	}

	@Override
	public String getTitle() {
		return question.getQuestionTitle();
	}

	@Override
	public void getData(Bundle arguments) {
		question = (Question) arguments.getSerializable("data");
	}

	@Override
	public void saveAnswer() {
		if (isAtleastOneOptionChecked()) {
			int id = radioGroup.getCheckedRadioButtonId();
			RadioButton checkedButton = (RadioButton) questionContainer.findViewById(id);
			String answer = checkedButton.getText().toString();
			String question = title.getText().toString();
			Answers.getInstance().put_answer(question, answer);
		}
	}
}