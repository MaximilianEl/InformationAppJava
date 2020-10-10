package com.example.informationappjava.ui.campus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *This is a ViewModel for the campus fragment
 */
public class CampusViewModel extends ViewModel {

  private final MutableLiveData<String> mText;


  public CampusViewModel() {
    mText = new MutableLiveData<>();
  }

  /**
   * @return
   */
  public LiveData<String> getText() {
    return mText;
  }
}