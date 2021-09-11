package net.lucode.hackware.magicindicator.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/** Created by yc on 2021/9/10
 **/
inline fun ViewPager.onPageScrollStateChanged(
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = { _ -> }
) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.invoke(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
        }
    })
}

inline fun ViewPager.onPageScrolled(
    crossinline onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _, _, _ -> }
) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled.invoke(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
        }
    })
}

inline fun ViewPager.onPageSelected(
    crossinline onPageSelected: (position: Int) -> Unit = { _ -> }
) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    })
}

fun ViewPager.smartScroll(index: Int) {
    if (index >= 0) {
        val viewPager = this
        val current = viewPager.currentItem
        if (current != index) {
            if (abs(current - index) > 1) {
                viewPager.setCurrentItem(index, false)
            } else {
                viewPager.currentItem = index
            }
        }
    }
}

fun ViewPager.jump(index: Int){
    setCurrentItem(index,false)
}

inline fun ViewPager2.onPageScrollStateChanged(
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = { _ -> }
) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged.invoke(state)
        }
    })
}

inline fun ViewPager2.onPageScrolled(
    crossinline onPageScrolled: (position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit = { _, _, _ -> }
) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            onPageScrolled.invoke(position, positionOffset, positionOffsetPixels)
        }
    })
}

inline fun ViewPager2.onPageSelected(
    crossinline onPageSelected: (position: Int) -> Unit = { _ -> }
) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    })
}

fun ViewPager2.smartScroll(index: Int) {
    val viewPager = this
    val current = viewPager.currentItem
    if (current != index) {
        if (abs(current - index) > 1) {
            viewPager.setCurrentItem(index, false)
        } else {
            viewPager.currentItem = index
        }
    }
}

fun ViewPager2.jump(index: Int){
    setCurrentItem(index,false)
}

fun FragmentActivity.getCurrentFragment(viewPager: ViewPager2) =
    supportFragmentManager.findFragmentByTag("f${viewPager.adapter?.getItemId(viewPager.currentItem)}")

fun Fragment.getCurrentFragment(viewPager: ViewPager2) =
    childFragmentManager.findFragmentByTag("f${viewPager.adapter?.getItemId(viewPager.currentItem)}")

fun ViewPager.getCurrentFragment(): Fragment? {
    val viewPager = this
    return viewPager.adapter?.instantiateItem(viewPager, viewPager.currentItem) as? Fragment
}