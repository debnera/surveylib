package com.androidadvance.androidsurvey.fragment;

import android.text.InputType;

public class FragmentTextSimple extends FragmentMultiline {
	@Override
	public void createQuestionContent() {
		super.createQuestionContent();
		answerView.setMaxLines(1);
	}
}