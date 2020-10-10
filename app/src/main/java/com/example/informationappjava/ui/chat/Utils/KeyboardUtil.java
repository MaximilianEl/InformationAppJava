package com.example.informationappjava.ui.chat.Utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 *KeyboardUtil class is needed to handle the keyboard of the device.
 */
public class KeyboardUtil {

  /**
   *The interface KeyboardVisibilityListener is used to handle visibility changes of the keyboard,
   */
  public interface KeyboardVisibilityListener {

    void onKeyboardVisibilityChanged(boolean keyboardVisible);
  }

  /**
   * The setKeyboardVisibilityListener() method is needed to handle different keyboard actions.
   * @param activity
   * @param keyboardVisibilityListener
   */
  public static void setKeyboardVisibilityListener(Activity activity,
      final KeyboardVisibilityListener keyboardVisibilityListener) {
    final View contentView = activity.findViewById(android.R.id.content);
    contentView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
      private int mPreviousHeight;

      @Override
      public void onGlobalLayout() {
        int newHeight = contentView.getHeight();
        if (mPreviousHeight != 0) {
          if (mPreviousHeight > newHeight) {
            // Height decreased: keyboard was shown
            keyboardVisibilityListener.onKeyboardVisibilityChanged(true);
          } else if (mPreviousHeight < newHeight) {
            // Height increased: keyboard was hidden
            keyboardVisibilityListener.onKeyboardVisibilityChanged(false);
          } else {
            // No change
          }
        }
        mPreviousHeight = newHeight;
      }
    });
  }
}
