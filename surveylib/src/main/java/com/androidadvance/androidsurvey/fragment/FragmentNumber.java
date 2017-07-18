package com.androidadvance.androidsurvey.fragment;

import android.text.InputType;

public class FragmentNumber extends FragmentMultiline {
    @Override
    public void createQuestionContent() {
        super.createQuestionContent();
        answerView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }
}
