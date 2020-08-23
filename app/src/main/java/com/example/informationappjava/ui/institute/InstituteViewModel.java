package com.example.informationappjava.ui.institute;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InstituteViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public InstituteViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is slideshow fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }
}