package com.example.informationappjava.ui.campus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import com.example.informationappjava.R;

public class ImageAdapter extends PagerAdapter {

  private Context campContext;
  private int[] campImgId = new int[]{R.drawable.camp1, R.drawable.camp2, R.drawable.camp3,
      R.drawable.camp4, R.drawable.camp5, R.drawable.camp6 };

  ImageAdapter(Context context) {
    campContext = context;
  }

  @Override
  public int getCount() {
    return campImgId.length;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ImageView imageView = new ImageView(campContext);
    imageView.setScaleType(ScaleType.CENTER_CROP);
    imageView.setImageResource(campImgId[position]);
    container.addView(imageView, 0);
    return imageView;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((ImageView) object);
  }
}