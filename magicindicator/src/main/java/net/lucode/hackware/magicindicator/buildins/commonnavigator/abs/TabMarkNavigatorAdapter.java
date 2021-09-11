package net.lucode.hackware.magicindicator.buildins.commonnavigator.abs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.R;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.lucode.hackware.magicindicator.listener.OnTabSelectListener;
import net.lucode.hackware.magicindicator.listener.OnTrackTabListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc on 2021/9/10
 **/
public class TabMarkNavigatorAdapter extends CommonNavigatorAdapter {
    private List<String> list = new ArrayList<>();
    private int marginLeftAndRight;
    private int mSelectedColor;
    private int mNormalColor;
    private int selectedBg;
    private int normalBg;
    private int textSize;
    private int textPaddingVertical;
    private int textPaddingHorizontal;
    private boolean isBold;
    private boolean isBoldNormal;
    private OnTabSelectListener listener;
    private OnTrackTabListener trackTabListener;

    private TabMarkNavigatorAdapter(List<String> list,
                                    int marginLeftAndRight,
                                    int selectColor,
                                    int normalColor,
                                    int selectedBg,
                                    int normalBg,
                                    boolean isBold,
                                    boolean isBoldNormal,
                                    int textSize,
                                    int textPaddingVertical,
                                    int textPaddingHorizontal,
                                    OnTabSelectListener listener,
                                    OnTrackTabListener trackTabListener) {

        this.list.clear();
        this.list.addAll(list);
        this.listener = listener;
        this.trackTabListener = trackTabListener;
        this.mSelectedColor = selectColor;
        this.mNormalColor = normalColor;
        this.textSize = textSize;
        this.textPaddingVertical = textPaddingVertical;
        this.textPaddingHorizontal = textPaddingHorizontal;
        this.isBold = isBold;
        this.isBoldNormal = isBoldNormal;
        this.marginLeftAndRight = marginLeftAndRight;
        this.selectedBg = selectedBg;
        this.normalBg = normalBg;
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

    @Override
    public int getCount() {
        return list.size();
    }

    @SuppressLint("InflateParams")
    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        View customLayout = LayoutInflater.from(context).inflate(R.layout.hlj_widget_mark_item,
                null);
        int padding = UIUtil.dip2px(context, marginLeftAndRight);
        customLayout.setPadding(padding, 0, padding, 0);
        TextView textView = customLayout.findViewById(R.id.text);
        int padding1 = UIUtil.dip2px(context, textPaddingVertical);
        int padding2 = UIUtil.dip2px(context, textPaddingHorizontal);
        textView.setPadding(padding2, padding1, padding2, padding1);
        textView.setTextSize(textSize);
        textView.setText(list.get(index));
        if (trackTabListener != null) {
            trackTabListener.onTrack(commonPagerTitleView, index);
        }
        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

            @Override
            public void onSelected(int index, int totalCount) {
                textView.setTextColor(mSelectedColor);
                textView.setBackgroundResource(selectedBg);
                if (isBold) {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }

            @Override
            public void onDeselected(int index, int totalCount) {
                textView.setTextColor(mNormalColor);
                textView.setBackgroundResource(normalBg);
                if (isBoldNormal) {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }

            @Override
            public void onLeave(int index, int totalCount, float leavePercent,
                                boolean leftToRight) {

            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent,
                                boolean leftToRight) {

            }
        });
        commonPagerTitleView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSelect(index);
            }
            ViewParent parent = commonPagerTitleView.getParent();
            try {
                ((ViewGroup) parent).requestLayout();
            } catch (Exception ignored) {

            }
        });
        commonPagerTitleView.setContentView(customLayout);
        return commonPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }


    public static class Builder {
        private int selectedColor = Color.parseColor("#342E25");
        private int normalColor = Color.parseColor("#3A3836");
        private int selectedBg = R.drawable.module_lib_widget_sp_r100_e8b45e;
        private int normalBg = R.drawable.module_lib_widget_sp_r100_ffead3;
        private int marginLeftAndRight = 4;
        private int textSize = 13;
        private int textPaddingVertical = 4;
        private int textPaddingHorizontal = 10;
        private OnTabSelectListener listener;
        private OnTrackTabListener trackTabListener;
        private boolean isBold = false;
        private boolean isBoldNormal = false;
        private List<String> list = new ArrayList<>();

        public Builder() {

        }

        public Builder(List<String> list) {
            this.list.clear();
            this.list.addAll(list);
        }

        public TabMarkNavigatorAdapter.Builder selectColor(int selectedColor) {
            this.selectedColor = selectedColor;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder normalColor(int normalColor) {
            this.normalColor = normalColor;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder selectedBg(int selectedBg) {
            this.selectedBg = selectedBg;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder normalBg(int normalBg) {
            this.normalBg = normalBg;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder marginLeftAndRight(int marginLeftAndRight) {
            this.marginLeftAndRight = marginLeftAndRight;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }


        public TabMarkNavigatorAdapter.Builder bold(boolean isBold) {
            this.isBold = isBold;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder boldNormal(boolean boldNormal) {
            this.isBoldNormal = boldNormal;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder TabSelectListener(OnTabSelectListener listener) {
            this.listener = listener;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder TrackTabListener(OnTrackTabListener listener) {
            this.trackTabListener = listener;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder setTextPaddingVertical(int textPaddingVertical) {
            this.textPaddingVertical = textPaddingVertical;
            return this;
        }

        public TabMarkNavigatorAdapter.Builder setTextPaddingHorizontal(int textPaddingHorizontal) {
            this.textPaddingHorizontal = textPaddingHorizontal;
            return this;
        }

        public TabMarkNavigatorAdapter build() {
            return new TabMarkNavigatorAdapter(
                    list,
                    marginLeftAndRight,
                    selectedColor,
                    normalColor,
                    selectedBg,
                    normalBg,
                    isBold,
                    isBoldNormal,
                    textSize,
                    textPaddingVertical,
                    textPaddingHorizontal,
                    listener,
                    trackTabListener
            );
        }
    }
}

