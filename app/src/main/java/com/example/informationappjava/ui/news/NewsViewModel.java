package com.example.informationappjava.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public NewsViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("Willkommen am Institut für Management und Technik am Campus Lingen");
  }

  public LiveData<String> getText() {
    return mText;
  }
}