package com.example.fooddiz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout layout;
    AppCompatTextView next, back, skip;
    ViewPagerAdapter viewPagerAdapter;
    AppCompatImageView appCompatImageView2, appCompatImageView;
    TextView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        skip = findViewById(R.id.skip);
        appCompatImageView = findViewById(R.id.appCompatImageView);
        appCompatImageView2 = findViewById(R.id.appCompatImageView2);

        appCompatImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentItem(0) > 0) {
                    viewPager.setCurrentItem(getCurrentItem(-1), true);
                }
            }
        });

        appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentItem(0) < 3) {
                    viewPager.setCurrentItem(getCurrentItem(1), true);
                } else {
                    //start the home activity
                    startActivity(new Intent(getApplicationContext(), ParentActivity.class));
                    finish();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ParentActivity.class));
                finish();
            }
        });

        viewPager = findViewById(R.id.slideViewPager);
        layout = findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        viewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpIndicator(int position) {
        dots = new TextView[4];
        layout.removeAllViews();

        for(int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.black, getApplicationContext().getTheme()));
            }
            layout.addView(dots[i]);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary, getApplicationContext().getTheme()));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);
            if(position > 0) {
                back.setVisibility(View.VISIBLE);
            } else {
                back.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getCurrentItem(int i) {
        return viewPager.getCurrentItem() + i;
    }
}
