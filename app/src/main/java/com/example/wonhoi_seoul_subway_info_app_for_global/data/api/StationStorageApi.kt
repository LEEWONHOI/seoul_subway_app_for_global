package com.example.wonhoi_seoul_subway_info_app_for_global.data.api

import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.StationEntity
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.StationIdAndEngNameEntity
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.SubwayEntity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class StationStorageApi(
    firebaseStorage: FirebaseStorage
) : StationApi {
    // 파이어베이스 에서 data.csv 를 가져온다.
    private val sheetReference = firebaseStorage.reference.child(STATION_DATA_FILE_NAME)

    override suspend fun getStationDataUpdatedTimeMillis(): Long =
    // metadata 는 Task 를 호출한다.
    // 그 Task 는 원래 늘 하는 것 처럼 리스너로 받는 대신
    // coroutines-play-services 를 의존성 추가해서 Task 를 await() 하도록 만듬
        // 그로인해 Task 가 완료될 때까지 await 를 하고 값을 리턴한다. 그래서 suspend 사용
        sheetReference.metadata.await().updatedTimeMillis


    override suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>> {
        val downloadSizeBytes = sheetReference.metadata.await().sizeBytes
        val byteArray = sheetReference.getBytes(downloadSizeBytes).await()

        // 다운받은 csv 데이터를 split 으로 다듬어주고, 0번째 값 (지하철 아이디) 과 1번째 값 (역 명)들을 각각 담아준다.
        return byteArray.decodeToString()
            .lines()
            .drop(1)
            .map { it.split(",") }
            .map {
               StationEntity(it[1], it[2], it[3]) to SubwayEntity(it[0].toInt())
            }
    }

    override suspend fun getStationIdAndEngName(): List<StationIdAndEngNameEntity> {
        val downloadSizeBytes = sheetReference.metadata.await().sizeBytes
        val byteArray = sheetReference.getBytes(downloadSizeBytes).await()

        return byteArray.decodeToString()
            .lines()
            .drop(1)
            .map { it.split(",") }
            .map {
                StationIdAndEngNameEntity(it[3], it[2])
            }
    }

    companion object {
        private const val STATION_DATA_FILE_NAME = "station_data_4_line.csv"
    }

}