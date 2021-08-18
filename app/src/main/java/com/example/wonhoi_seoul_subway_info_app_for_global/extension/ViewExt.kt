package com.example.wonhoi_seoul_subway_info_app_for_global.extension

import android.view.View
import androidx.annotation.Px

@Px
fun View.dip(dipValue: Float) = context.dip(dipValue)

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}