package com.example.informationappjava.ui.campus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import androidx.viewpager.widget.PagerAdapter;
import com.example.informationappjava.R;

/**
 *The ImageAdapter class is needed to handle the image gallery of the campus fragment.
 *This class is used to insert the images into the campus fragment.
 */
public class ImageAdapter extends PagerAdapter {

  private final Context campContext;
  private final int[] campImgId = new int[]{R.drawable.camp1, R.drawable.camp2, R.drawable.camp3,
      R.drawable.camp4, R.drawable.camp5, R.drawable.camp6};

  ImageAdapter(Context context) {
    campContext = context;
  }

  /**
   * @return the length of the campus image id.
   */
  @Override
  public int getCount() {
    return campImgId.length;
  }

  /**
   * The isViewFromObeject () method checks if the view is the same as the object.
   * @param view
   * @param object
   * @return
   */
  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  /**
   *
   * @param container
   * @param position
   * @return
   */
  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ImageView imageView = new ImageView(campContext);
    imageView.setScaleType(ScaleType.CENTER_CROP);
    imageView.setImageResource(campImgId[position]);
    container.addView(imageView, 0);
    return imageView;
  }

  /**
   * @param container
   * @param position
   * @param object
   */
  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((ImageView) object);
  }


}
