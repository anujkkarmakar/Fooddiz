package com.example.fooddiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int[] images = {
            R.drawable.welcome,
            R.drawable.peoples
    };

    int[] descriptions = {
            R.string.desc_one,
            R.string.desc_two
    };

    int[] texts = {
            R.string.heading_one,
            R.string.heading_two
    };

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return texts.length;
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);
        AppCompatImageView slideImage = (AppCompatImageView) view.findViewById(R.id.sliderImage);
        AppCompatTextView slideText = (AppCompatTextView) view.findViewById(R.id.sliderText);
        AppCompatTextView slideDescription = (AppCompatTextView) view.findViewById(R.id.sliderDescription);

        slideImage.setImageResource(images[position]);
        slideText.setText(texts[position]);
        slideDescription.setText(descriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}