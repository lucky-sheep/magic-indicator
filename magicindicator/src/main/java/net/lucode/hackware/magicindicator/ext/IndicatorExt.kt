package net.lucode.hackware.magicindicator.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.xueyu.kotlinextlibrary.color
import com.xueyu.kotlinextlibrary.dp
import com.xueyu.kotlinextlibrary.onPageSelected
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.R
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.ViewPagerHelper2
import net.lucode.hackware.magicindicator.adapter.TabNormalNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.TabMarkNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LineIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionTitleView

/** Created by yc on 2021/9/10
 **/
internal val onTabMockClickListener = View.OnClickListener {

}

/**
 * tabChildAt
 *
 * @param position
 */
internal fun MagicIndicator.tabChildAt(position: Int): View? {
    return (navigator as? CommonNavigator)?.getPagerTitleView(position) as? View
}

/**
 * HljTab.Normal
 */
fun MagicIndicator.propertyNormal(
    contentDescription: String = "一级tab",
    wrap: Boolean = true,
    defaultIndex: Int = 0,
    needTrackerWhenNotClick: Boolean = true,
    list: List<String>? = null,
    isExactly: Boolean = true,
    isBold: Boolean = true,
    isBoldNormal: Boolean = true,
    leftPadding: Int = 2.dp,
    rightPadding: Int = 2.dp,
    selectColor: Int = color(R.color.colorBlack2),
    normalColor: Int = color(R.color.colorBlack2),
    indicatorColor: Int = color(R.color.colorPrimary),
    marginLeftAndRight: Int = 14,
    textSize: Int = 16,
    selectTextSize: Int = 16,
    indicatorWidth: Int = 20,
    indicatorHeight: Int = 3,
    onTabSelect: ((index: Int) -> Unit)? = null,
    onTabTrack: ((view: View, index: Int) -> Unit)? = null,
    viewPager: ViewPager? = null,
    viewPager2: ViewPager2? = null
): TabNormalNavigatorAdapter {
    var currentIndex = defaultIndex
    val indicator = this
    indicator.contentDescription = contentDescription
    val commonNavigator = CommonNavigator(context).apply {
        this.isAdjustMode = !wrap
        this.leftPadding = leftPadding
        this.rightPadding = rightPadding
    }
    val builder = if (list.isNullOrEmpty()) {
        TabNormalNavigatorAdapter.Builder()
    } else {
        TabNormalNavigatorAdapter.Builder(list)
    }
    val typeface = ResourcesCompat.getFont(context, R.font.alibaba_puhuiti_regular)
    val adapter = builder
        .isExactly(isExactly)
        .bold(isBold)
        .boldNormal(isBoldNormal)
        .selectColor(selectColor)
        .normalColor(normalColor)
        .indicatorColor(indicatorColor)
        .marginLeftAndRight(marginLeftAndRight)
        .textSize(textSize)
        .selectTextSize(selectTextSize)
        .indicatorWidth(indicatorWidth)
        .indicatorHeight(indicatorHeight)
        .TabSelectListener {
            when {
                onTabSelect != null -> {
                    onTabSelect.invoke(it)
                }
                viewPager2 != null -> {
                    viewPager2.smartScroll(it)
                }
                viewPager != null -> {
                    viewPager.smartScroll(it)
                }
                else -> {
                    indicator.onPageSelected(it)
                    indicator.onPageScrolled(it, 0f, 0)
                }
            }
        }
        .BoldListener { text, bold ->
            typeface?.let {
                text.typeface = it
            }
            text.paint.isFakeBoldText = bold
        }
        .TrackTabListener { view, index ->
            onTabTrack?.invoke(view, index)
        }
        .build()
    commonNavigator.adapter = adapter
    indicator.navigator = commonNavigator
    viewPager?.let {
        ViewPagerHelper.bind(indicator, it)
        it.onPageSelected { position ->
            if (needTrackerWhenNotClick && currentIndex != position) {
                currentIndex = position
                tabChildAt(position)?.let { child ->
                    onTabMockClickListener.onClick(child)
                }
            }
        }
    }
    viewPager2?.let {
        ViewPagerHelper2.bind(indicator, it)
        it.onPageSelected { position ->
            if (needTrackerWhenNotClick && currentIndex != position) {
                currentIndex = position
                tabChildAt(position)?.let { child ->
                    onTabMockClickListener.onClick(child)
                }
            }
        }
    }
    return adapter
}

/**
 * HljTab.Secondary
 */
fun MagicIndicator.propertyMark(
    contentDescription: String = "次级tab",
    wrap: Boolean = true,
    defaultIndex: Int = 0,
    needTrackerWhenNotClick: Boolean = true,
    list: List<String>? = null,
    isBold: Boolean = false,
    isBoldNormal: Boolean = false,
    leftPadding: Int = 12.dp,
    rightPadding: Int = 12.dp,
    selectColor: Int = color(R.color.colorPrimary),
    normalColor: Int = color(R.color.colorBlack2),
    selectBg: Int = R.drawable.module_ui_component_sp_r100_light,
    normalBg: Int = R.drawable.module_ui_component_sp_r100_f6f6f7,
    marginLeftAndRight: Int = 4,
    textSize: Int = 14,
    textPaddingVertical: Int = 6,
    textPaddingHorizontal: Int = 16,
    onTabSelect: ((index: Int) -> Unit)? = null,
    onTabTrack: ((view: View, index: Int) -> Unit)? = null,
    viewPager: ViewPager? = null,
    viewPager2: ViewPager2? = null
): TabMarkNavigatorAdapter {
    var currentIndex = defaultIndex
    val indicator = this
    indicator.contentDescription = contentDescription
    val commonNavigator = CommonNavigator(indicator.context).apply {
        this.isAdjustMode = !wrap
        this.leftPadding = leftPadding
        this.rightPadding = rightPadding
    }
    val builder = if (list.isNullOrEmpty()) {
        TabMarkNavigatorAdapter.Builder()
    } else {
        TabMarkNavigatorAdapter.Builder(list)
    }
    val adapter = builder
        .bold(isBold)
        .boldNormal(isBoldNormal)
        .selectColor(selectColor)
        .normalColor(normalColor)
        .selectedBg(selectBg)
        .normalBg(normalBg)
        .marginLeftAndRight(marginLeftAndRight)
        .textSize(textSize)
        .setTextPaddingVertical(textPaddingVertical)
        .setTextPaddingHorizontal(textPaddingHorizontal)
        .TabSelectListener {
            when {
                onTabSelect != null -> {
                    onTabSelect.invoke(it)
                }
                viewPager2 != null -> {
                    viewPager2.smartScroll(it)
                }
                viewPager != null -> {
                    viewPager.smartScroll(it)
                }
                else -> {
                    indicator.onPageSelected(it)
                    indicator.onPageScrolled(it, 0f, 0)
                }
            }
        }
        .TrackTabListener { view, index ->
            onTabTrack?.invoke(view, index)
        }
        .build()
    commonNavigator.adapter = adapter
    indicator.navigator = commonNavigator
    viewPager?.let {
        ViewPagerHelper.bind(indicator, it)
        it.onPageSelected { position ->
            if (needTrackerWhenNotClick && currentIndex != position) {
                currentIndex = position
                tabChildAt(position)?.let { child ->
                    onTabMockClickListener.onClick(child)
                }
            }
        }
    }
    viewPager2?.let {
        ViewPagerHelper2.bind(indicator, it)
        it.onPageSelected { position ->
            if (needTrackerWhenNotClick && currentIndex != position) {
                currentIndex = position
                tabChildAt(position)?.let { child ->
                    onTabMockClickListener.onClick(child)
                }
            }
        }
    }
    return adapter
}

/**
 * HljTab.Pic
 */
fun MagicIndicator.propertyPicTab(
    contentDescription: String = "图片上tab",
    selectColor: Int = Color.WHITE,
    normalColor: Int = Color.WHITE,
    gradientColors: IntArray? = intArrayOf(
        Color.parseColor("#FF587E"),
        color(R.color.colorPrimary)
    ),
    indicatorBg: Int = R.drawable.module_ui_component_sp_r10_66000000,
    list: List<String>,
    onTabSelect: ((index: Int) -> Unit)? = null
) {
    this.contentDescription = contentDescription
    setBackgroundResource(indicatorBg)
    val commonNavigator = CommonNavigator(context)
    val adapter = object : CommonNavigatorAdapter() {
        override fun getCount() = list.size
        override fun getTitleView(context: Context, index: Int) =
            ColorTransitionTitleView(context).apply {
                this.text = list[index]
                this.includeFontPadding = false
                this.normalColor = normalColor
                this.updatePadding(left = 8.dp, right = 8.dp)
                this.textSize = 11f
                this.selectedColor = selectColor
                setOnClickListener {
                    onTabSelect?.invoke(index)
                }
            }

        override fun getIndicator(context: Context) =
            LineIndicator(context).apply {
                lineHeight = 20f.dp
                roundRadius = 10f.dp
                setLinearGradientColor(gradientColors)
            }
    }
    commonNavigator.adapter = adapter
    navigator = commonNavigator
}