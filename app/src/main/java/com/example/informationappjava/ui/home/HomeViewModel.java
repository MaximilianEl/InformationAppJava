package com.example.informationappjava.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * HomeViewModel.
 */
public class HomeViewModel extends ViewModel {

  private final MutableLiveData<String> mText;

  /**
   *
   */
  public HomeViewModel() {
    mText = new MutableLiveData<>();
  }

  /**
   * @return
   */
  public LiveData<String> getText() {
    return mText;
  }
}