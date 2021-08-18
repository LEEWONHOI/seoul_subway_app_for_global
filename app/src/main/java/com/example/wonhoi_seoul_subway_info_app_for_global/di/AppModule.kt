package com.example.wonhoi_seoul_subway_info_app_for_global.di

import android.app.Activity
import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.StationApi
import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.StationArrivalsApi
import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.StationStorageApi
import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.Url
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.AppDatabase
import com.example.wonhoi_seoul_subway_info_app_for_global.data.preference.PreferenceManager
import com.example.wonhoi_seoul_subway_info_app_for_global.data.preference.SharedPreferenceManager
import com.example.wonhoi_seoul_subway_info_app_for_global.data.repository.StationRepository
import com.example.wonhoi_seoul_subway_info_app_for_global.data.repository.StationRepositorylmpl
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station
import com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stationarrivals.StationArrivalsViewModel
import com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stations.StationsViewModel
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single { Dispatchers.IO }

    // DataBase
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().stationDao() }

    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }




    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if(BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    // Api (seoul)
    single<StationArrivalsApi> {
        Retrofit.Builder().baseUrl(Url.SEOUL_DATA_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    // Api (Firebase)
    single<StationApi> { StationStorageApi(Firebase.storage) }

    // Repository
    single<StationRepository> { StationRepositorylmpl(get(), get(), get(), get(), get()) }

    // ViewModel
    viewModel {  StationsViewModel(get()) }
    viewModel {  (station: Station) -> StationArrivalsViewModel(station, get()) }


}




