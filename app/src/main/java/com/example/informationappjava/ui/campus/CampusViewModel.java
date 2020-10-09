package com.example.informationappjava.ui.campus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *
 */
public class CampusViewModel extends ViewModel {

  private final MutableLiveData<String> mText;

  /**
   *
   */
  public CampusViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is gallery fragment");
  }

  /**
   * @return
   */
  public LiveData<String> getText() {
    return mText;
  }
}