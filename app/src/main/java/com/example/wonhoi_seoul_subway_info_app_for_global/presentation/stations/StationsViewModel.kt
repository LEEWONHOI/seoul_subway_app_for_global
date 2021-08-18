package com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wonhoi_seoul_subway_info_app_for_global.R
import com.example.wonhoi_seoul_subway_info_app_for_global.data.repository.StationRepository
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station
import com.example.wonhoi_seoul_subway_info_app_for_global.presentation.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class StationsViewModel(
    private val stationRepository: StationRepository,
) : BaseViewModel() {

    private var _stationLiveData = MutableLiveData<StationsState>(StationsState.Uninitialized)
    val stationLiveData: LiveData<StationsState> = _stationLiveData

    // 검색
    private val queryString: MutableStateFlow<String> = MutableStateFlow("")
    // 지하철 역
    private val stations: MutableStateFlow<List<Station>> = MutableStateFlow(emptyList())

    override fun fetchData(): Job = viewModelScope.launch  {
        observeStation()
        _stationLiveData.value = StationsState.Success(
            stations = stations.value
        )
        stationRepository.refreshStations()
    }

    fun filterStations(query: String) = viewModelScope.launch {
        queryString.emit(query)
    }

    private fun observeStation() {
        stationRepository
            .stations
            .combine(queryString) { listStations, query ->  // combine 함수로 인해 먼저 방출된 value 값부터 combine 한다. (즉, '서' 만 처도 나오는 방식)
                if (query.isBlank()) {
                    listStations
                } else {
                    listStations.filter {
                        it.engName.contains(query, true)  // query 에 있는 값이 포함된 값을 필터링한다.
                    }
                }
            }
            .onStart {
                _stationLiveData.value = StationsState.Loading
            }
            .onEach {
                stations.value = it
                _stationLiveData.value = StationsState.Success(
                    stations = stations.value
                )
            }
            .catch {
                it.printStackTrace()
                _stationLiveData.value = StationsState.Error(
                    R.string.can_find_station_info
                )
            }
            .launchIn(MainScope()) // collect() 함. Main scope 를 사용하겠다는 것
    }

    fun onDestroy() {
        MainScope().cancel()
    }

    fun toggleStationFavorite(station: Station) = viewModelScope.launch {
        stationRepository.updateStation(station.copy(isFavorited = !station.isFavorited))
    }
}