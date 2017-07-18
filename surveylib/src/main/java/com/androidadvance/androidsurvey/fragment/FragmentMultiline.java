package com.androidadvance.androidsurvey.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.models.Question;

public class FragmentMultiline extends QuestionFragment {

    private Question question;
	EditText answerView;

    public void createQuestionContent() {
        answerView = new EditText(activity);
	    answerView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
	    answerView.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    }

		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    }

		    @Override
		    public void afterTextChanged(Editable s) {
			    updateContinueButtonVisibility();
		    }
	    });
        questionContainer.addView(answerView);
    }

    @Override
    public boolean isContinueButtonVisible() {
        return !question.getRequired() || getAnswerLength() > 1;
    }

    private int getAnswerLength() {
	    return answerView.getText().length();
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
        if (getAnswerLength() > 1) {
            String answer = answerView.getText().toString();
            String question = title.getText().toString();
            Answers.getInstance().put_answer(question, answer);
        }
    }
}