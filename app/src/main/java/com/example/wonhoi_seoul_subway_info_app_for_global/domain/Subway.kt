package com.example.wonhoi_seoul_subway_info_app_for_global.domain

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.room.PrimaryKey

enum class Subway(
    @PrimaryKey
    val id : Int,
    val label : String,
    @ColorInt val color: Int
) {
    LINE_1(1001, "LINE 1", Color.parseColor("#FF0D3692")),

    LINE_2(1002, "LINE 2", Color.parseColor("#FF33A23D")),

    LINE_3(1003, "LINE 3", Color.parseColor("#FFFE5D10")),

    LINE_4(1004, "LINE 4", Color.parseColor("#FF00A2D1")),

    LINE_5(1005, "LINE 5", Color.parseColor("#FF8B50A4")),

    LINE_6(1006, "LINE 6", Color.parseColor("#FFC55C1D")),

    LINE_7(1007, "LINE 7", Color.parseColor("#FF54640D")),

    LINE_8(1008, "LINE 8", Color.parseColor("#FFF14C82")),

    LINE_9(1009, "LINE 9", Color.parseColor("#FFAA9872")),

    LINE_63(1063, "Gyeongui", Color.parseColor("#FF73C7A6")),

    LINE_65(1065, "Airport", Color.parseColor("#FF3681B7")),

    LINE_67(1067, "Gyeongchun", Color.parseColor("#FF32C6A6")),

    LINE_71(1071, "SuinBundang", Color.parseColor("#FFFF8C00")),

    LINE_75(1075, "SuinBundang", Color.parseColor("#FFFF8C00")),

    LINE_77(1077, "Shinbundang", Color.parseColor("#FFC82127")),

    UNKNOWN(-1, "UNKOWN", Color.LTGRAY);

    companion object {
        fun findById(id: Int): Subway = values().find { it.id == id } ?: UNKNOWN
    }
}

