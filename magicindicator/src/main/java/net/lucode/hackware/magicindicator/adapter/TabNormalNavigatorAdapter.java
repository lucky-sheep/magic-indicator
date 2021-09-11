package net.lucode.hackware.magicindicator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.CaptivePortal;
import android.view.ViewGroup;
import android.view.ViewParent;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.R;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.listener.OnBoldListener;
import net.lucode.hackware.magicindicator.listener.OnTabSelectListener;
import net.lucode.hackware.magicindicator.listener.OnTrackTabListener;
import net.lucode.hackware.magicindicator.model.TabTwoFloorModel;
import net.lucode.hackware.magicindicator.titles.ScaleTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

import static net.lucode.hackware.magicindicator.core.GlobalConfigurationKt.app$delegate;
import static net.lucode.hackware.magicindicator.core.GlobalConfigurationKt.getApp;

/**
 * Created by yc on 2021/9/10
 **/
public class TabNormalNavigatorAdapter extends CommonNavigatorAdapter {
    private int mSelectedColor;
    private int mNormalColor;
    private int indicatorColor;
    private int marginLeftAndRight;
    private int textSize;
    private int indicatorWidth;
    private int indicatorHeight;
    private int selectTextSize;
    private boolean isTwoFloor;
    private boolean isBold;
    private boolean isBoldNormal;
    private boolean isExactly;
    private List<String> list = new ArrayList<>();
    private List<TabTwoFloorModel> twoFloorModels = new ArrayList<>();
    private OnTabSelectListener listener;
    private OnTrackTabListener trackTabListener;
    private OnBoldListener boldListener;
    private FragmentContainerHelper helper;
    private int preIndex = -1;

    private TabNormalNavigatorAdapter(List<String> list,
                                      List<TabTwoFloorModel> twoFloorModels,
                                      boolean isTwoFloor,
                                      int selectColor,
                                      int normalColor,
                                      int indicatorColor,
                                      int marginLeftAndRight,
                                      int indicatorWidth,
                                      int indicatorHeight,
                                      int textSize,
                                      int selectTextSize,
                                      boolean isBold,
                                      boolean isBoldNormal,
                                      boolean isExactly,
                                      OnTabSelectListener listener,
                                      OnTrackTabListener trackTabListener,
                                      OnBoldListener boldListener) {
        this.list.clear();
        this.list.addAll(list);
        this.twoFloorModels.clear();
        this.twoFloorModels.addAll(twoFloorModels);
        this.isTwoFloor = isTwoFloor;
        this.listener = listener;
        this.trackTabListener = trackTabListener;
        this.mSelectedColor = selectColor;
        this.mNormalColor = normalColor;
        this.indicatorColor = indicatorColor;
        this.indicatorWidth = indicatorWidth;
        this.indicatorHeight = indicatorHeight;
        this.textSize = textSize;
        this.selectTextSize = selectTextSize;
        this.isBold = isBold;
        this.isBoldNormal = isBoldNormal;
        this.isExactly = isExactly;
        this.marginLeftAndRight = marginLeftAndRight;
        this.boldListener = boldListener;
    }

    public void setData(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<String> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setContainerHelper(FragmentContainerHelper helper) {
        this.helper = helper;
    }

    public FragmentContainerHelper getHelper() {
        return helper;
    }

    @Override
    public int getCount() {
        return isTwoFloor ? twoFloorModels.size() : list.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        ScaleTransitionPagerTitleView simplePagerTitleView =
                new ScaleTransitionPagerTitleView(context) {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        super.onSelected(index, totalCount);
                        if (boldListener != null) {
                            boldListener.onBold(this, isBold);
                        }
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        super.onDeselected(index, totalCount);
                        if (boldListener != null) {
                            boldListener.onBold(this, isBoldNormal);
                        }
                    }
                };
        String title = isTwoFloor ? twoFloorModels.get(index).getTitle() : list.get(index);
        simplePagerTitleView.setText(title);

        if (trackTabListener != null) {
            trackTabListener.onTrack(simplePagerTitleView, index);
        }
        simplePagerTitleView.setTextSize(selectTextSize);
        simplePagerTitleView.setMinScale(textSize * 1f / selectTextSize);
        int padding = UIUtil.dip2px(context, marginLeftAndRight);
        simplePagerTitleView.setPadding(padding, 0, padding, 0);
        simplePagerTitleView.setNormalColor(mNormalColor);
        simplePagerTitleView.setSelectedColor(mSelectedColor);
        simplePagerTitleView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSelect(index);
            }
            ViewParent parent = simplePagerTitleView.getParent();
            try {
                ((ViewGroup) parent).requestLayout();
            } catch (Exception ignored) {

            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        if (isExactly) {
            indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        }
        indicator.setLineHeight(UIUtil.dip2px(context, indicatorHeight));
        indicator.setRoundRadius(UIUtil.dip2px(context, indicatorHeight));
        indicator.setLineWidth(UIUtil.dip2px(context, indicatorWidth));
        indicator.setColors(indicatorColor);
        return indicator;
    }

    public static class Builder {
        private int selectedColor = ContextCompat.getColor(getApp(),
                R.color.colorPrimary);
        private int normalColor = Color.parseColor("#000000");
        private int indicatorColor = ContextCompat.getColor(getApp(),
                R.color.colorPrimary);
        private int marginLeftAndRight = 12;
        private int textSize = 16;
        private int selectTextSize = 16;
        private int indicatorWidth = 20;
        private int indicatorHeight = 3;
        private OnTabSelectListener listener;
        private boolean isBold = true;
        private boolean isBoldNormal = false;
        private List<String> list = new ArrayList<>();
        private List<TabTwoFloorModel> twoFloorList = new ArrayList<>();
        private boolean isTwoFloor;
        private boolean isExactly = true;
        private OnTrackTabListener trackTabListener;
        private OnBoldListener boldListener;

        public Builder() {

        }

        public Builder(List<String> list) {
            this.list.clear();
            this.list.addAll(list);
        }

        public Builder(List<TabTwoFloorModel> list, boolean isTwoFloor) {
            this.twoFloorList.clear();
            this.twoFloorList.addAll(list);
            this.isTwoFloor = isTwoFloor;
        }

        public Builder marginLeftAndRight(int marginLeftAndRight) {
            this.marginLeftAndRight = marginLeftAndRight;
            return this;
        }

        public Builder selectColor(int selectedColor) {
            this.selectedColor = selectedColor;
            return this;
        }

        public Builder bold(boolean isBold) {
            this.isBold = isBold;
            return this;
        }

        public Builder boldNormal(boolean boldNormal) {
            this.isBoldNormal = boldNormal;
            return this;
        }

        public Builder normalColor(int normalColor) {
            this.normalColor = normalColor;
            return this;
        }

        public Builder indicatorColor(int indicatorColor) {
            this.indicatorColor = indicatorColor;
            return this;
        }

        public Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder selectTextSize(int selectTextSize) {
            this.selectTextSize = selectTextSize;
            return this;
        }

        public Builder TabSelectListener(OnTabSelectListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder indicatorWidth(int indicatorWidth) {
            this.indicatorWidth = indicatorWidth;
            return this;
        }

        public Builder indicatorHeight(int indicatorHeight) {
            this.indicatorHeight = indicatorHeight;
            return this;
        }

        public Builder isExactly(boolean isExactly) {
            this.isExactly = isExactly;
            return this;
        }

        public Builder TrackTabListener(OnTrackTabListener listener) {
            this.trackTabListener = listener;
            return this;
        }

        public Builder BoldListener(OnBoldListener listener) {
            this.boldListener = listener;
            return this;
        }

        public TabNormalNavigatorAdapter build() {
            return new TabNormalNavigatorAdapter(
                    list,
                    twoFloorList,
                    isTwoFloor,
                    selectedColor,
                    normalColor,
                    indicatorColor,
                    marginLeftAndRight,
                    indicatorWidth,
                    indicatorHeight,
                    textSize,
                    selectTextSize,
                    isBold,
                    isBoldNormal,
                    isExactly,
                    listener,
                    trackTabListener,
                    boldListener
            );
        }
    }
}
