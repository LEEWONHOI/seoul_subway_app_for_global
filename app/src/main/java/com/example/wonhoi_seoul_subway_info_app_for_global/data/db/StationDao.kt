package com.example.wonhoi_seoul_subway_info_app_for_global.data.db

import androidx.room.*
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {

    @Transaction
    @Query("SELECT * FROM StationEntity")
    fun getStationWithSubways(): Flow<List<StationWithSubwayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStations(station: List<StationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubways(subway: List<SubwayEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossReferences(reference: List<StationSubwayCrossRefEntity>)

    @Transaction
    suspend fun insertStationSubways(stationSubways: List<Pair<StationEntity, SubwayEntity>>) {
        insertStations(stationSubways.map { it.first })
        insertSubways(stationSubways.map { it.second })

        insertCrossReferences(
            stationSubways.map { (station, subway) ->
                StationSubwayCrossRefEntity(
                    station.stationName,
                    subway.subwayId,
                )
            }
        )
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdAndEngNameCrossReferences(reference: List<StationIdAndEngNameEntity>)

    @Query("SELECT * FROM StationIdAndEngNameEntity")
    suspend fun getStations() : List<StationIdAndEngNameEntity>

    @Update
    suspend fun updateStation(station: StationEntity)

}