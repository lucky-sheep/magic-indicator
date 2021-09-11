package net.lucode.hackware.magicindicator;

import androidx.viewpager2.widget.ViewPager2;

/**
 * Created by yc on 2021/9/10
 **/
public class ViewPagerHelper2 {
    public static void bind(final MagicIndicator magicIndicator, ViewPager2 vp) {
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
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
    }
}