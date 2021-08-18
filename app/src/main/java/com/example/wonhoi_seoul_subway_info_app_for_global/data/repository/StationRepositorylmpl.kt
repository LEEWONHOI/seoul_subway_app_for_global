package com.example.wonhoi_seoul_subway_info_app_for_global.data.repository

import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.StationApi
import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.StationArrivalsApi
import com.example.wonhoi_seoul_subway_info_app_for_global.data.api.response.mapper.toArrivalInformation
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.StationDao
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.mapper.toStationEntitiy
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.mapper.toStationIdAndEngNameModel
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.mapper.toStations
import com.example.wonhoi_seoul_subway_info_app_for_global.data.preference.PreferenceManager
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.ArrivalInformation
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.Station
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.StationIdAndEngNameModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StationRepositorylmpl(
    private val stationArrivalsApi: StationArrivalsApi,
    private val stationApi: StationApi,
    private val stationDao: StationDao,
    private val preferenceManager: PreferenceManager,
    private val dispatcher: CoroutineDispatcher
) : StationRepository {

    override val stations: Flow<List<Station>> =
        stationDao.getStationWithSubways()
            .distinctUntilChanged() // 관찰값을 반복해서 호출하는 걸 방지. You can ensure that the UI is only notified when the actual query results change by applying the distinctUntilChanged()
            .map {
                it.toStations()
                    .sortedByDescending { station -> // sortedByDescending 값을 내린 차순으로 정렬해줌
                        station.isFavorited
                    }
            }
            .flowOn(dispatcher) // 어떤 쓰레드에서 작동할지 -> IO 쓰레드


    override suspend fun refreshStations() = withContext(dispatcher) {    // DB 업데이트
        val fileUpdatedTimeMillis = stationApi.getStationDataUpdatedTimeMillis()
        val lastDatabaseUpdatedTimeMillis =
            preferenceManager.getLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS)    // Key 값 전달

        if (lastDatabaseUpdatedTimeMillis == null || fileUpdatedTimeMillis > lastDatabaseUpdatedTimeMillis) {
            // DB가 최신 버전이라면, 데이터베이스에 데이터 인서트
            stationDao.insertStationSubways(stationApi.getStationSubways())
            stationDao.insertIdAndEngNameCrossReferences(stationApi.getStationIdAndEngName())


            // 타임 스탬프 찍기
            preferenceManager.putLong(KEY_LAST_DATABASE_UPDATED_TIME_MILLIS, fileUpdatedTimeMillis)
        }
    }

    override suspend fun getStationArrivals(stationName: String): List<ArrivalInformation> =
        withContext(dispatcher) {
            stationArrivalsApi.getRealtimeStationArrivals(stationName)
                .body()
                ?.realtimeArrivalList
                ?.toArrivalInformation()
                ?.distinctBy { it.direction }   // 중복되는 값 중에서 가장 첫번째 것을 가지고 온다.
                ?.sortedBy { it.subway }        // 지하철 라인 별로 보여준다.
                ?: throw RuntimeException("Failed to get arrival information.")
        }

    override suspend fun updateStation(station: Station) = withContext(dispatcher) {
        stationDao.updateStation(station.toStationEntitiy())
    }

    override suspend fun getStationIdAndName(): List<StationIdAndEngNameModel> =
        withContext(dispatcher) {
            stationDao.getStations().toStationIdAndEngNameModel()
        }

    companion object {
        private const val KEY_LAST_DATABASE_UPDATED_TIME_MILLIS =
            "KEY_LAST_DATABASE_UPDATED_TIME_MILLIS"
    }

}