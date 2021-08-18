package com.example.wonhoi_seoul_subway_info_app_for_global.presentation.stationarrivals

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wonhoi_seoul_subway_info_app_for_global.R
import com.example.wonhoi_seoul_subway_info_app_for_global.databinding.ItemArrivalBinding
import com.example.wonhoi_seoul_subway_info_app_for_global.domain.ArrivalInformation

class StationArrivalsAdapter(
    private val stationArrivalsFragment: StationArrivalsFragment
) : RecyclerView.Adapter<StationArrivalsAdapter.ViewHolder>() {

    var data: List<ArrivalInformation> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemArrivalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val viewBinding: ItemArrivalBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(arrival: ArrivalInformation) {
            viewBinding.labelTextView.badgeColor = arrival.subway.color
            viewBinding.labelTextView.text =
                "${arrival.subway.label} - " + arrival.direction
                    .replace("상행", "up train")
                    .replace("하행", "down train")
                    .replace("내선", "inner circle line")
                    .replace("외선", "outer circle line")

            viewBinding.destinationTextView.text =
                "🚩 ${changeSubwayNextStationToEnglishName(arrival)}"

            viewBinding.arrivalMessageTextView.text =
                arrival.thisStationMessage
                    .replace("전역", "previous station")
                    .replace("도착", "arrival")
                    .replace("출발", "departure")
                    .replace("진입", "approaching")
                    .replace("분", "min")
                    .replace("초", "sec")
                    .replace("후", "after")
                    .replace("번째", "before")

            viewBinding.arrivalMessageTextView.setTextColor(
                if (arrival.prevStationMessage.contains("previous station") &&
                    !arrival.thisStationMessage.contains("번째")
                ) {
                    Color.BLUE
                } else if (arrival.thisStationMessage.contains("this station")) {
                    Color.RED
                } else Color.DKGRAY
            )

            viewBinding.prevStationImage.setImageResource(
                if (arrival.prevStationMessage.contains("previous station") &&
                    !arrival.thisStationMessage.contains("번째")
                ) {
                    R.drawable.ic_baseline_directions_subway_24_prev_station
                } else R.drawable.ic_baseline_directions_subway_24_black
            )

            viewBinding.thisStationImage.setImageResource(
                if (arrival.thisStationMessage.contains("this station")) {
                    R.drawable.ic_baseline_directions_subway_24_this_station
                } else R.drawable.ic_baseline_directions_subway_24_black
            )

            viewBinding.updatedTimeTextView.text = "measurement time: ${arrival.updatedAt}"

            viewBinding.nextStationTextView.text = changeSubwayNextStationToEnglishName(arrival)
            viewBinding.thisStationTextView.text = changeSubwayThisStationToEnglishName(arrival)
            viewBinding.prevStationTextView.text = changeSubwayPrevStationToEnglishName(arrival)
        }

        private fun changeSubwayNextStationToEnglishName(arrival: ArrivalInformation) =
            arrival.nextStationName.toRegex().replace(arrival.nextStationName) {
                    stationArrivalsFragment.stationHashMap[arrival.nextStationName]
                        ?: arrival.nextStationName
                }

        private fun changeSubwayThisStationToEnglishName(arrival: ArrivalInformation) =
            arrival.thisStationName.toRegex().replace(arrival.thisStationName) {
                stationArrivalsFragment.stationHashMap[arrival.thisStationName]
                    ?: arrival.thisStationName
            }

        private fun changeSubwayPrevStationToEnglishName(arrival: ArrivalInformation) =
            arrival.preStationName.toRegex().replace(arrival.preStationName) {
                stationArrivalsFragment.stationHashMap[arrival.preStationName]
                    ?: arrival.preStationName
            }


    }
}