package com.example.wonhoi_seoul_subway_info_app_for_global.data.preference

interface PreferenceManager {

    fun getLong(key:String) : Long?

    fun putLong(key:String, value : Long)

}