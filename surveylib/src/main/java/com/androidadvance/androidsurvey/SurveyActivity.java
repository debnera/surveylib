package com.androidadvance.androidsurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.androidadvance.androidsurvey.adapters.AdapterFragmentQ;
import com.androidadvance.androidsurvey.fragment.FragmentCheckboxes;
import com.androidadvance.androidsurvey.fragment.FragmentEnd;
import com.androidadvance.androidsurvey.fragment.FragmentMultiline;
import com.androidadvance.androidsurvey.fragment.FragmentNumber;
import com.androidadvance.androidsurvey.fragment.FragmentRadioboxes;
import com.androidadvance.androidsurvey.fragment.FragmentStart;
import com.androidadvance.androidsurvey.fragment.FragmentTextSimple;
import com.androidadvance.androidsurvey.models.Question;
import com.androidadvance.androidsurvey.models.SurveyPojo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SurveyActivity extends FragmentActivity {

    private SurveyPojo surveyPojo;
    private ViewPager viewPager;
    private String style = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_survey);
        if (getIntent().getExtras() != null) {
	        parseIntentExtras();
        }
        viewPager = (ViewPager) findViewById(R.id.pager);
	    List<Fragment> questionFragments = parseSurveyFragments();
        AdapterFragmentQ pagerAdapter = new AdapterFragmentQ(getSupportFragmentManager(), questionFragments);
        viewPager.setAdapter(pagerAdapter);
    }

	public void goToNext() {
		viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
	}

	@Override
	public void onBackPressed() {
		if (viewPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
		}
	}

	public void eventSurveyCompleted(Answers instance) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("answers", instance.get_json_object());
		setResult(Activity.RESULT_OK, returnIntent);
		finish();
	}

	private void parseIntentExtras() {
		Bundle extras = getIntent().getExtras();
		surveyPojo = new Gson().fromJson(extras.getString("json_survey"), SurveyPojo.class);
		if (extras.containsKey("style")) {
			style = extras.getString("style");
		}
	}

    private List<Fragment> parseSurveyFragments() {
	    ArrayList<Fragment> questionFragments = new ArrayList<>();
		Bundle surveyProperties = parseProperties();

	    // Intro
	    if (!surveyPojo.getSurveyProperties().getSkipIntro()) {
		    questionFragments.add(parseIntro(surveyProperties));
	    }

	    // Questions
	    for (Question question : surveyPojo.getQuestions()) {
		    Fragment fragment = parseQuestion(question);
		    if (fragment != null) {
			    questionFragments.add(fragment);
		    }
	    }

	    // Outro
	    questionFragments.add(parseEnd(surveyProperties));
	    return questionFragments;
    }

    private Bundle parseProperties() {
	    Bundle bundle = new Bundle();
	    bundle.putSerializable("survery_properties", surveyPojo.getSurveyProperties());
	    bundle.putString("style", style);
	    return bundle;
    }

	private Fragment parseIntro(Bundle properties) {
		Fragment fragment = new FragmentStart();
		fragment.setArguments(properties);
		return fragment;
	}

	private Fragment parseEnd(Bundle properties) {
		Fragment fragment = new FragmentEnd();
		fragment.setArguments(properties);
		return fragment;
	}

    private Fragment parseQuestion(Question question) {
        Fragment fragment = getFragmentOfType(question.getQuestionType());
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", question);
            bundle.putString("style", style);
            fragment.setArguments(bundle);
            return fragment;
        }
        else {
            return null;
        }
    }

    private Fragment getFragmentOfType(String type) {
        Fragment fragment;
        switch (type) {
            case "String":
                fragment = new FragmentTextSimple();
                break;
            case "Checkboxes":
                fragment = new FragmentCheckboxes();
                break;
            case "Radioboxes":
                fragment = new FragmentRadioboxes();
                break;
            case "Number":
                fragment = new FragmentNumber();
                break;
            case "StringMultiline":
                fragment = new FragmentMultiline();
                break;
            default:
	            Log.e("SurveyLib", "Invalid fragment type: " + type);
                fragment = null;
        }
        return fragment;
    }
}
