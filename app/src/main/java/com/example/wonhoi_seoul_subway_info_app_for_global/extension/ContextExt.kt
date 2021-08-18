package com.example.wonhoi_seoul_subway_info_app_for_global.extension

import android.content.Context
import androidx.annotation.Px

@Px
fun Context.dip(dipValue: Float) = (dipValue * resources.displayMetrics.density).toInt()