package com.example.magicindicatordemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

public class MainActivity extends AppCompatActivity {
    private MagicIndicator magicIndicator;
    private ViewPager2 viewPager;
    private final String[] title = new String[]{"RecyclerView", "ListView", "GridLayout"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        magicIndicator = findViewById(R.id.magicIndicator);
        viewPager = findViewById(R.id.view_pager);
        MyAdapter myAdapter = new MyAdapter(this);
        viewPager.setAdapter(myAdapter);
        initMagicindicator();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });

//         //viewPager1的写法
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                magicIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                magicIndicator.onPageSelected(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                magicIndicator.onPageScrollStateChanged(state);
//            }
//        });

    }

    @SuppressLint("ResourceAsColor")
    private void initMagicindicator() {
        magicIndicator.setBackgroundColor(R.color.black);
        //新建导航栏
        CommonNavigator commonNavigator = new CommonNavigator(this);
//        commonNavigator.setFollowTouch(true);

//        commonNavigator.setScrollPivotX(0.5f);
        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setRightPadding(50);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
               @Override
               public int getCount() {
                   return title == null ? 0 : title.length;
               }

               @Override
               public IPagerTitleView getTitleView(Context context, final int index) {
                   CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                   commonPagerTitleView.setContentView(R.layout.title_pager);
                   final TextView textView = commonPagerTitleView.findViewById(R.id.textview);
//                   final ImageView imageView = commonPagerTitleView.findViewById(R.id.im_view);
                   textView.setText(title[index]);
                   commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                       @Override
                       public void onSelected(int index, int totalCount) {
                           textView.setTextColor(Color.RED);
                       }

                       @Override
                       public void onDeselected(int index, int totalCount) {
                           textView.setTextColor(Color.BLACK);
                       }

                       @Override
                       public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
//                           imageView.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
//                           imageView.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                       }

                       @Override
                       public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
//                           imageView.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
//                           imageView.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                       }
                   });
                   commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           viewPager.setCurrentItem(index);
                       }
                   });
                   return commonPagerTitleView;
               }


               @Override
               public IPagerIndicator getIndicator(Context context) {
                   //设置标题指示器，也有多种,可不选，即没有标题指示器。
                   BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                   indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4")
                           , Color.parseColor("#76b0ff"), Color.parseColor("#c683fe")
                           , Color.parseColor("#76b0ff"), Color.parseColor("#c683fe")
                           , Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                   return null;
               }
       });

        magicIndicator.setNavigator(commonNavigator);
    }

        @Override
        public void onWindowFocusChanged(boolean hasFocus) {
            super.onWindowFocusChanged(hasFocus);
        }

        class MyAdapter extends FragmentStateAdapter {

            public MyAdapter(FragmentActivity fm) {
                super(fm);
            }

            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new FragmentOne();
                        break;
                    case 1:
                        fragment = new FragmentTwo();
                        break;
                    case 2:
                        fragment = new FragmentThree();
                        break;
                }

                return fragment;
            }

            @Override
            public int getItemCount() {
                return title.length;
            }

        }


}