package net.lucode.hackware.magicindicator.ext

import android.view.View
import androidx.annotation.Px

/** Created by yc on 2021/9/10
 **/
inline fun View.updatePadding(
    @Px left: Int = paddingLeft,
    @Px top: Int = paddingTop,
    @Px right: Int = paddingRight,
    @Px bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}