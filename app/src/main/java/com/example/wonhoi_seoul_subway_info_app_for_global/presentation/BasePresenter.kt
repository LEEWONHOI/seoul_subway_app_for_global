package com.example.wonhoi_seoul_subway_info_app_for_global.presentation

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

interface BasePresenter {

    val scope : CoroutineScope
    get() = MainScope()

    fun onViewCreated()

    fun onDestroyView()

    @CallSuper
    fun onDestroy() {
        scope.cancel()
    }

}