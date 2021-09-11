package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder

/** Created by yc on 2021/9/10
 **/
internal class ColorTransitionTitleView(context: Context) : SimplePagerTitleView(context) {
    override fun onLeave(
        index: Int,
        totalCount: Int,
        leavePercent: Float,
        leftToRight: Boolean
    ) {
        if (leavePercent > 0) {
            val color = ArgbEvaluatorHolder.eval(leavePercent, mSelectedColor, mNormalColor)
            setTextColor(color)
        }
    }

    override fun onEnter(
        index: Int,
        totalCount: Int,
        enterPercent: Float,
        leftToRight: Boolean
    ) {
        if (enterPercent > 0) {
            val color = ArgbEvaluatorHolder.eval(enterPercent, mNormalColor, mSelectedColor)
            setTextColor(color)
        }
    }
}