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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout layout;
    AppCompatTextView next, back, skip;
    ViewPagerAdapter viewPagerAdapter;
    TextView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        skip = findViewById(R.id.skip);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentItem(0) < 1) {
                    viewPager.setCurrentItem(getCurrentItem(1), true);
                } else {
                    //start the home activity
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentItem(0) > 0) {
                    viewPager.setCurrentItem(getCurrentItem(-1), true);
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        viewPager = findViewById(R.id.slideViewPager);
        layout = findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

    }

    public void setUpIndicator(int position) {

        //TODO: To increase the count to minimum 4 and maximum 5 based upon proper consultation with team
        dots = new TextView[2];
        layout.removeAllViews();

        for(int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.black, getApplicationContext().getTheme()));
            }
            layout.addView(dots[i]);;
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
