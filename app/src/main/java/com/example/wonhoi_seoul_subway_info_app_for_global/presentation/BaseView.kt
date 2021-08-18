package com.example.wonhoi_seoul_subway_info_app_for_global.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter : PresenterT

}