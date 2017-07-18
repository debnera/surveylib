package com.androidadvance.androidsurvey.fragment;

import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.models.Question;

import java.util.ArrayList;
import java.util.List;

public class FragmentCheckboxes extends QuestionFragment {

    private Question question;
    private List<CheckBox> checkBoxes;

    public void createQuestionContent() {
	    checkBoxes = new ArrayList<>();
	    for (String choice : question.getChoices()) {
		    CheckBox cb = new CheckBox(activity);
		    cb.setText(Html.fromHtml(choice));
		    cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		    cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		    questionContainer.addView(cb);
		    checkBoxes.add(cb);

		    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			    @Override
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				    updateContinueButtonVisibility();
			    }
		    });
	    }
    }

    @Override
    public boolean isContinueButtonVisible() {
        return !question.getRequired() || isAtleastOneOptionChecked();
    }

    private List<CheckBox> getChecked() {
	    List<CheckBox> result = new ArrayList<>();
	    for (CheckBox cb : checkBoxes) {
		    if (cb.isChecked()) {
			    result.add(cb);
		    }
	    }
	    return result;
    }

    private String getAnswers() {
	    List<CheckBox> checked = getChecked();
	    String answer = "";
	    String separator = "";
	    for (CheckBox checkBox : checked) {
		    answer += separator;
		    answer += checkBox.getText().toString();
		    separator = ", ";
	    }
	    return answer;
    }

    private boolean isAtleastOneOptionChecked() {
	    for (CheckBox cb : checkBoxes) {
		    if (cb.isChecked()) {
			    return true;
		    }
	    }
        return false;
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
	        String answer = getAnswers();
            String question = title.getText().toString();
            Answers.getInstance().put_answer(question, answer);
        }
    }
}