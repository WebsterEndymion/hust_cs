package com.team429.hometeacher.ui.home;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.team429.hometeacher.ui.question.QuestionFragment;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Fragment> mFragment;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        mFragment = new MutableLiveData<>();
        mFragment.setValue(new QuestionFragment());
    }

    public LiveData<Fragment> getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment.setValue(fragment);
    }

    public LiveData<String> getText() {
        return mText;
    }
}