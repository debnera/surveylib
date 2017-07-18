package com.androidadvance.androidsurvey.fragment;


public class FragmentTextSimple extends FragmentMultiline {
	@Override
	public void createQuestionContent() {
		super.createQuestionContent();
		answerView.setMaxLines(1);
	}
}